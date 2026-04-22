package com.easy.core.ui.compose.refresh

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

enum class SmartSwipeStateFlag {
    IDLE,
    REFRESHING,
    SUCCESS,
    ERROR,
    TIPS_DOWN,
    TIPS_RELEASE
}

sealed interface ThresholdScrollStrategy {
    data object None : ThresholdScrollStrategy

    data object UnLimited : ThresholdScrollStrategy

    data class Fixed(val height: Float) : ThresholdScrollStrategy
}

@Composable
fun rememberSmartSwipeRefreshState(): SmartSwipeRefreshState {
    return remember {
        SmartSwipeRefreshState()
    }
}

class SmartSwipeRefreshState {
    var stickinessLevel = 0.5f

    var dragHeaderIndicatorStrategy: ThresholdScrollStrategy = ThresholdScrollStrategy.UnLimited

    var dragFooterIndicatorStrategy: ThresholdScrollStrategy = ThresholdScrollStrategy.UnLimited

    var flingHeaderIndicatorStrategy: ThresholdScrollStrategy = ThresholdScrollStrategy.None

    var flingFooterIndicatorStrategy: ThresholdScrollStrategy = ThresholdScrollStrategy.None

    var needFirstRefresh = false

    var headerHeight = 0f

    var footerHeight = 0f

    var enableRefresh = true

    var enableLoadMore = true

    var releaseIsEdge = false

    var refreshFlag by mutableStateOf(SmartSwipeStateFlag.IDLE)

    var loadMoreFlag by mutableStateOf(SmartSwipeStateFlag.IDLE)

    var animateIsOver by mutableStateOf(true)

    private val indicatorOffsetState = Animatable(0f)

    private val mutatorMutex = MutatorMutex()

    val indicatorOffset: Float
        get() = indicatorOffsetState.value

    fun isLoading(): Boolean {
        return !animateIsOver ||
            refreshFlag == SmartSwipeStateFlag.REFRESHING ||
            loadMoreFlag == SmartSwipeStateFlag.REFRESHING
    }

    suspend fun animateOffsetTo(offset: Float) {
        mutatorMutex.mutate {
            indicatorOffsetState.animateTo(offset) {
                if (this.value == 0f) {
                    animateIsOver = true
                }
            }
        }
    }

    suspend fun snapOffsetTo(offset: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            indicatorOffsetState.snapTo(offset)

            if (indicatorOffset >= headerHeight) {
                refreshFlag = SmartSwipeStateFlag.TIPS_RELEASE
            } else if (indicatorOffset <= -footerHeight) {
                loadMoreFlag = SmartSwipeStateFlag.TIPS_RELEASE
            } else {
                if (indicatorOffset > 0) {
                    refreshFlag = SmartSwipeStateFlag.TIPS_DOWN
                }
                if (indicatorOffset < 0) {
                    loadMoreFlag = SmartSwipeStateFlag.TIPS_DOWN
                }
            }
        }
    }

    suspend fun initRefresh() {
        snapOffsetTo(headerHeight)
        refreshFlag = SmartSwipeStateFlag.REFRESHING
    }

    fun strategyIndicatorHeight(strategy: ThresholdScrollStrategy): Float {
        return when (strategy) {
            ThresholdScrollStrategy.None -> 0f
            is ThresholdScrollStrategy.Fixed -> strategy.height
            ThresholdScrollStrategy.UnLimited -> Float.MAX_VALUE
        }
    }
}

@Composable
fun SmartSwipeRefresh(
    modifier: Modifier = Modifier,
    state: SmartSwipeRefreshState,
    onRefresh: (suspend () -> Unit)? = null,
    onLoadMore: (suspend () -> Unit)? = null,
    headerIndicator: @Composable (() -> Unit)? = null,
    footerIndicator: @Composable (() -> Unit)? = null,
    contentScrollState: ScrollableState? = null,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val connection = remember(coroutineScope) {
        SmartSwipeRefreshNestedScrollConnection(state, coroutineScope)
    }

    LaunchedEffect(state.refreshFlag) {
        when (state.refreshFlag) {
            SmartSwipeStateFlag.REFRESHING -> {
                state.animateIsOver = false
                onRefresh?.invoke()
            }

            SmartSwipeStateFlag.ERROR, SmartSwipeStateFlag.SUCCESS -> {
                delay(500)
                state.animateOffsetTo(0f)
            }

            else -> Unit
        }
    }

    LaunchedEffect(state.loadMoreFlag) {
        when (state.loadMoreFlag) {
            SmartSwipeStateFlag.REFRESHING -> {
                state.animateIsOver = false
                onLoadMore?.invoke()
            }

            SmartSwipeStateFlag.ERROR, SmartSwipeStateFlag.SUCCESS -> {
                delay(500)
                state.animateOffsetTo(0f)
            }

            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        if (state.needFirstRefresh) {
            state.initRefresh()
        }
    }

    LaunchedEffect(state.indicatorOffset) {
        if (state.indicatorOffset < 0 && state.loadMoreFlag != SmartSwipeStateFlag.SUCCESS) {
            contentScrollState?.dispatchRawDelta(-state.indicatorOffset)
        }
    }

    Box(
        modifier = modifier.clipToBounds()
    ) {
        SubComposeSmartSwipeRefresh(
            headerIndicator = headerIndicator,
            footerIndicator = footerIndicator
        ) { header, footer ->
            state.headerHeight = header.toFloat()
            state.footerHeight = footer.toFloat()

            Box(modifier = Modifier.nestedScroll(connection)) {
                val offset = state.indicatorOffset
                val contentModifier = when {
                    offset > 0f -> Modifier.graphicsLayer { translationY = offset }
                    offset < 0f && contentScrollState != null -> Modifier
                    offset < 0f -> Modifier.graphicsLayer { translationY = offset }
                    else -> Modifier
                }

                Box(modifier = contentModifier.fillMaxSize()) {
                    content()
                }

                headerIndicator?.let {
                    Box(
                        modifier = Modifier.graphicsLayer {
                            translationY = -header.toFloat() + state.indicatorOffset
                        }
                    ) {
                        headerIndicator()
                    }
                }

                footerIndicator?.let {
                    Box(
                        modifier = Modifier.graphicsLayer {
                            translationY = footer.toFloat() + state.indicatorOffset
                        }
                    ) {
                        footerIndicator()
                    }
                }
            }
        }
    }
}

private class SmartSwipeRefreshNestedScrollConnection(
    private val state: SmartSwipeRefreshState,
    private val coroutineScope: CoroutineScope
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return when {
            state.isLoading() -> Offset.Zero
            available.y < 0 && state.indicatorOffset > 0 -> {
                val canConsumed = (available.y * state.stickinessLevel)
                    .coerceAtLeast(0 - state.indicatorOffset)
                scroll(canConsumed)
            }

            available.y > 0 && state.indicatorOffset < 0 -> {
                val canConsumed = (available.y * state.stickinessLevel)
                    .coerceAtMost(0 - state.indicatorOffset)
                scroll(canConsumed)
            }

            else -> Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return when {
            state.isLoading() -> Offset.Zero
            available.y > 0 && state.enableRefresh && state.headerHeight != 0f -> {
                val canConsumed = if (source == NestedScrollSource.Fling) {
                    (available.y * state.stickinessLevel).coerceAtMost(
                        state.strategyIndicatorHeight(state.flingHeaderIndicatorStrategy) - state.indicatorOffset
                    )
                } else {
                    (available.y * state.stickinessLevel).coerceAtMost(
                        state.strategyIndicatorHeight(state.dragHeaderIndicatorStrategy) - state.indicatorOffset
                    )
                }
                scroll(canConsumed)
            }

            available.y < 0 && state.enableLoadMore && state.footerHeight != 0f -> {
                val canConsumed = if (source == NestedScrollSource.Fling) {
                    (available.y * state.stickinessLevel).coerceAtLeast(
                        -state.strategyIndicatorHeight(state.flingFooterIndicatorStrategy) - state.indicatorOffset
                    )
                } else {
                    (available.y * state.stickinessLevel).coerceAtLeast(
                        -state.strategyIndicatorHeight(state.dragFooterIndicatorStrategy) - state.indicatorOffset
                    )
                }
                scroll(canConsumed)
            }

            else -> Offset.Zero
        }
    }

    private fun scroll(canConsumed: Float): Offset {
        return if (canConsumed.absoluteValue > 0.5f) {
            coroutineScope.launch {
                state.snapOffsetTo(state.indicatorOffset + canConsumed)
            }
            Offset(0f, canConsumed / state.stickinessLevel)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: androidx.compose.ui.unit.Velocity): androidx.compose.ui.unit.Velocity {
        if (state.isLoading()) {
            return androidx.compose.ui.unit.Velocity.Zero
        }

        state.releaseIsEdge = state.indicatorOffset != 0f

        if (state.indicatorOffset >= state.headerHeight && state.releaseIsEdge) {
            if (state.refreshFlag != SmartSwipeStateFlag.REFRESHING) {
                state.refreshFlag = SmartSwipeStateFlag.REFRESHING
                state.animateOffsetTo(state.headerHeight)
                return available
            }
        }

        if (state.indicatorOffset <= -state.footerHeight && state.releaseIsEdge) {
            if (state.loadMoreFlag != SmartSwipeStateFlag.REFRESHING) {
                state.loadMoreFlag = SmartSwipeStateFlag.REFRESHING
                state.animateOffsetTo(-state.footerHeight)
                return available
            }
        }
        return super.onPreFling(available)
    }

    override suspend fun onPostFling(
        consumed: androidx.compose.ui.unit.Velocity,
        available: androidx.compose.ui.unit.Velocity
    ): androidx.compose.ui.unit.Velocity {
        if (state.isLoading()) {
            return androidx.compose.ui.unit.Velocity.Zero
        }
        if (state.refreshFlag != SmartSwipeStateFlag.REFRESHING && state.indicatorOffset > 0) {
            state.refreshFlag = SmartSwipeStateFlag.IDLE
            state.animateOffsetTo(0f)
        }
        if (state.loadMoreFlag != SmartSwipeStateFlag.REFRESHING && state.indicatorOffset < 0) {
            state.loadMoreFlag = SmartSwipeStateFlag.IDLE
            state.animateOffsetTo(0f)
        }
        return super.onPostFling(consumed, available)
    }
}

@Composable
private fun SubComposeSmartSwipeRefresh(
    headerIndicator: (@Composable () -> Unit)?,
    footerIndicator: (@Composable () -> Unit)?,
    content: @Composable (header: Int, footer: Int) -> Unit
) {
    SubcomposeLayout { constraints ->
        val headerPlaceable = subcompose("header", headerIndicator ?: {}).firstOrNull()?.measure(constraints)
        val footerPlaceable = subcompose("footer", footerIndicator ?: {}).firstOrNull()?.measure(constraints)
        val contentPlaceable = subcompose("content") {
            content(headerPlaceable?.height ?: 0, footerPlaceable?.height ?: 0)
        }.first().measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

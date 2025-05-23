package com.easy.core.msa

import com.easy.core.msa.IManageStartActivity
import com.easy.core.msa.ManageStartActivity

/**
 *
 * Project Name : ManageStartActivity
 * Package Name : com.itxca.msa
 * Create Time  : 2021-09-23 17:23
 * Create By    : @author xIao
 * Version      : 1.0.0
 *
 **/

/**
 * [com.itxca.msa.IManageStartActivity]
 */
typealias IMsa = IManageStartActivity

/**
 * [com.itxca.msa.ManageStartActivity]
 */
fun msa(): ManageStartActivity = ManageStartActivity()
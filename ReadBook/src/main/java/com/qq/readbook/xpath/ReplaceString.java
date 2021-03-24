package com.qq.readbook.xpath;

import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.core.Function;
import org.seimicrawler.xpath.core.Scope;
import org.seimicrawler.xpath.core.XValue;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.xpath
 * @Date : 上午 8:58
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class ReplaceString  implements Function {
    @Override
    public String name() {
        return "replace";
    }

    @Override
    public XValue call(Scope scope, List<XValue> params) {
        String newChar = params.get(0).asString();
        String oldChar = params.get(1).asString();
        String string = params.get(2).asString();
        return XValue.create(string.replace(oldChar,newChar));

    }
}

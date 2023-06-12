package org.eai.utils;

import io.quarkus.panache.common.Page;
import org.eai.domain.utils.TestConstants;

import java.util.Map;

public final class TestsUtility {
    private TestsUtility(){ }
    public static Map<String, Page> getPageQueryParam(){
        Page page = Page.of(0, 20);

        Map<String, Page> queryParam = Map.of(TestConstants.PAGE, page);
        return queryParam;
    }
}

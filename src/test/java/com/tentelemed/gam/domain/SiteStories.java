package com.tentelemed.gam.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 11:52
 */
public class SiteStories extends Stories {
    @Override
    protected List<Object> mySteps() {
        List<Object> steps = new ArrayList<>();
        steps.add(new SiteSteps());
        return steps;
    }
}

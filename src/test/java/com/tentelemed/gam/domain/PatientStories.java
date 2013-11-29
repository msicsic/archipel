package com.tentelemed.gam.domain;

import com.tentelemed.archipel.domain.Stories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 11:52
 */
public class PatientStories extends Stories {
    @Override
    protected List<Object> mySteps() {
        List<Object> steps = new ArrayList(1);
        steps.add(new PatientSteps());
        return steps;
    }
}

package com.example.jianjianhong.rxjavademo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Admin on 2015/10/21.
 */
public class ActivityClass {
    private String activityName;
    private Class activityClass;

    public ActivityClass() {}

    public ActivityClass(String activityName, Class activityClass) {
        this.activityName = activityName;
        this.activityClass = activityClass;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    @Override
    public String toString() {
        return activityName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                        append(activityName).
                        toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ActivityClass))
            return false;
        if (obj == this)
            return true;

        ActivityClass rhs = (ActivityClass) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(activityName, rhs.activityName).
                        isEquals();
    }
}

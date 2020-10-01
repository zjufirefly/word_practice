package auto;

import java.util.Random;

public enum TimeType {

    NOW("现在", 1), PAST("过去", 2), ALL("全部", 3);

    private String name;
    private int index;

    private TimeType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static TimeType getTimeType(int index) {
        for (TimeType c : TimeType.values()) {
            if (c.index == index) {
                return c;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

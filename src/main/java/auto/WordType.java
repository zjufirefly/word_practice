package auto;

public enum WordType {

    MESI("名词", 1), DEKI("可能", 2), ALL("全部", 3);

    private String name;
    private int index;

    private WordType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static WordType getWordInfo(int index) {
        for (WordType c : WordType.values()) {
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

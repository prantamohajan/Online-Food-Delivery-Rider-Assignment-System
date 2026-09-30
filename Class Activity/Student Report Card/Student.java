public class Student {
    private String name;
    private int mark1;
    private int mark2;

    public Student(String name, int mark1, int mark2) {
        this.name = name;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }

    public Student(String name) {
        this(name, 0, 0);
    }

    public String getName() {
        return name;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public double getAverage() {
        return (mark1 + mark2) / 2.0;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

    @Override
    public String toString() {
        return name + " -> marks: " + mark1 + ", " + mark2 + ", average: " + getAverage();
    }

    public boolean hasHigherAverage(Student other) {
        return this.getAverage() > other.getAverage();
    }

    public void addBonus(int bonus) {
        mark1 += bonus;
        System.out.println(name + " receives " + bonus + " bonus marks.");
    }

    public void addBonus(int bonus, String reason) {
        addBonus(bonus);
        System.out.println("Reason: " + reason);
    }
}
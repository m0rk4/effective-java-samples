package by.morka.effective.java.generalobjectmethods.clone;

public class NotCloneSample {

    private long x = 2;

    @Override
    protected NotCloneSample clone() throws CloneNotSupportedException {
        return (NotCloneSample) super.clone();
    }
}

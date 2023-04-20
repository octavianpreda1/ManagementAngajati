package Lab6;

import java.time.LocalDate;

public class Angajati {

    private String numele;
    private String postul;

    private LocalDate data_angajarii;

    private float salariul;

    public Angajati(){}
    public Angajati(String numele, String postul, LocalDate data_angajarii, float salariul) {
        this.numele = numele;
        this.postul = postul;
        this.data_angajarii = data_angajarii;
        this.salariul = salariul;
    }

    public String getNumele() {
        return numele;
    }

    public void setNumele(String numele) {
        this.numele = numele;
    }

    public String getPostul() {
        return postul;
    }

    public void setPostul(String postul) {
        this.postul = postul;
    }

    public LocalDate getData_angajarii() {
        return data_angajarii;
    }

    public void setData_angajarii(LocalDate data_angajarii) {
        this.data_angajarii = data_angajarii;
    }

    public float getSalariul() {
        return salariul;
    }

    public void setSalariul(float salariul) {
        this.salariul = salariul;
    }

    @Override
    public String toString() {
        return numele + ", "+ postul + ", " + data_angajarii + ", " + salariul;
    }
}

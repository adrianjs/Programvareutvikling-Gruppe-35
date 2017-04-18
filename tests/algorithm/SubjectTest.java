package algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class SubjectTest {
    Subject subject = new Subject("TDT4100", "Skriftlig eksamen", "Objektorientert programmering", "hal@ntnu.no");

    @Test
    public void testOfSubjectStructureWithGetters(){
        assertEquals("TDT4100", subject.getSubjectCode());
        assertEquals("Skriftlig eksamen", subject.getEvaluation());
        assertEquals("Objektorientert programmering", subject.getDescription());
        assertEquals("hal@ntnu.no", subject.getCoordinatorEmail());
    }

}
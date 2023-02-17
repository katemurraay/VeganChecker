package com.kmm.vegancheckerapp.features.Scanning.ResultsTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {LikelyVeganTest.class,
        NonVeganResultTests.class,
        NonVeganProductionResultTests.class,
        VeganResultTest.class,
        UploadedResultTest.class
        }
)
public class ResultsTestSuite {

}

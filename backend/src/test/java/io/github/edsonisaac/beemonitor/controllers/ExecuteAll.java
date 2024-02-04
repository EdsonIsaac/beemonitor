package io.github.edsonisaac.beemonitor.controllers;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AuthenticationControllerTest.class,
        HiveControllerTest.class,
        MensurationControllerTest.class,
        UserControllerTest.class
})
public class ExecuteAll { }
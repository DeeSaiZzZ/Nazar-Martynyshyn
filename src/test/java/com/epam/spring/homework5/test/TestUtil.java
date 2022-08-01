package com.epam.spring.homework5.test;

import com.epam.spring.homework5.model.Favor;
import com.epam.spring.homework5.model.Master;
import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.User;
import com.epam.spring.homework5.model.enums.Role;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.model.enums.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestUtil {

    public static final int START_PAGE_NUM = 0;

    private TestUtil() {
        //Required to avoid creating object of class
    }

    /*Entities*/

    //Create user entity without order
    public static User createTestUser() {
        return new User(1, "TestUser1", "Test1", "test@mail.com", null,
                Collections.emptyList(), Role.DEFAULT);
    }

    //Create user entity with order
    public static User createTestUserWithOrderList() throws ParseException {
        User user = new User(1, "TestUser1", "Test1", "test@mail.com", null, null, Role.DEFAULT);

        String oldString = "2022-08-20 19:30:00";
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(oldString);

        int idIndex = 1;

        user.setUsersOrder(List.of(new Order(idIndex++, user, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.AWAITING_PAYMENT, date, null),

                new Order(idIndex++, user, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.PAID, date, null),

                new Order(idIndex++, user, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.AWAITING_PAYMENT, date, null),

                new Order(idIndex, user, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.COMPLETE, date, date)
        ));

        return user;
    }

    //Create Favor entity with timetable
    public static Favor createTestFavor() {
        return new Favor(1, "TestName", Speciality.HAIRDRESSER, 500);
    }

    //Create Master entity without timetable
    public static Master createTestMasterWithoutTimetable() {
        return new Master(1, "NMaster1", "SMaster1",
                "master@gmail.com", null,
                null, Role.MASTER, 3.4, Collections.emptyList(), Speciality.HAIRDRESSER);
    }

    //Create Master entity with timetable
    public static Master createTestMasterWithTimetable() throws ParseException {
        Master master = new Master(1, "NMaster1", "SMaster1",
                "master@gmail.com", null,
                null, Role.MASTER, 3.4, null, Speciality.HAIRDRESSER);

        String oldString = "2022-08-20 19:30:00";
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(oldString);

        int idIndex = 1;

        ArrayList<Order> collect = Stream.of(new Order(idIndex++, createTestUser(), master, createTestFavor(), Status.AWAITING_PAYMENT, date, null),
                        new Order(idIndex++, createTestUser(), master, createTestFavor(), Status.PAID, date, null),
                        new Order(idIndex, null, master, createTestFavor(), Status.FREE, date, null))
                .collect(Collectors.toCollection(ArrayList::new));
        master.setTimeTable(collect);

        return master;
    }

    //Create Order entity with timetable
    public static Order createTestOrder() throws ParseException {
        String oldString = "2022-08-20 19:30:00";
        return new Order(1, null, createTestMasterWithoutTimetable(), createTestFavor(),
                Status.FREE, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(oldString), null);
    }

    /*Entity Lists*/

    //Create Favor entity list with timetable
    public static List<Favor> createFavorTestList() {
        int idIndex = 1;

        return List.of(new Favor(idIndex++, "TestName1", Speciality.HAIRDRESSER, 500),
                new Favor(idIndex++, "TestName2", Speciality.BEAUTICIAN, 300),
                new Favor(idIndex++, "TestName3", Speciality.HAIRDRESSER, 500),
                new Favor(idIndex++, "TestName4", Speciality.HAIRDRESSER, 700),
                new Favor(idIndex++, "TestName5", Speciality.MANICURIST, 500),
                new Favor(idIndex++, "TestName6", Speciality.MANICURIST, 100),
                new Favor(idIndex++, "TestName7", Speciality.HAIRDRESSER, 500),
                new Favor(idIndex, "TestName8", Speciality.BEAUTICIAN, 200));
    }

    //Create user entity list
    public static List<User> createUserTestList() {
        int idIndex = 1;

        return List.of(new User(idIndex++, "TestUser1", "Test1",
                        "test1@mail.com", null, null, Role.DEFAULT),

                new User(idIndex++, "TestUser2", "Test2",
                        "test2@mail.com", null, null, Role.DEFAULT),

                new User(idIndex++, "TestUser3", "Test3",
                        "test3@mail.com", null, null, Role.DEFAULT),

                new User(idIndex, "TestUser4", "Test4",
                        "test4@mail.com", null, null, Role.DEFAULT));
    }

    //Create Master entity list
    public static List<Master> createMasterTestList() {
        int idIndex = 1;

        return List.of(new Master(idIndex++, "NMaster1", "SMaster1",
                        "master1@gmail.com", "someTest1Master12345",
                        null, Role.MASTER, 4.2, null, Speciality.BEAUTICIAN),

                new Master(idIndex++, "NMaster2", "SMaster2",
                        "master2@gmail.com", "someTest2Master12345",
                        null, Role.MASTER, 3.8, null, Speciality.HAIRDRESSER),

                new Master(idIndex++, "NMaster3", "SMaster3",
                        "master3@gmail.com", "someTest3Master12345",
                        null, Role.MASTER, 2.2, null, Speciality.MANICURIST),

                new Master(idIndex++, "NMaster4", "SMaster4",
                        "master4@gmail.com", "someTest4Master12345",
                        null, Role.MASTER, 3.8, null, Speciality.MANICURIST),

                new Master(idIndex++, "NMaster5", "SMaster5",
                        "master5@gmail.com", "someTest5Master12345",
                        null, Role.MASTER, 3.8, null, Speciality.BEAUTICIAN),

                new Master(idIndex++, "NMaster6", "SMaster6",
                        "master6@gmail.com", "someTest6Master12345",
                        null, Role.MASTER, 3.8, null, Speciality.HAIRDRESSER),

                new Master(idIndex++, "NMaster7", "SMaster7",
                        "master7@gmail.com", "someTest7Master12345",
                        null, Role.MASTER, 3.8, null, Speciality.HAIRDRESSER),

                new Master(idIndex, "NMaster8", "SMaster8",
                        "master8@gmail.com", "someTest8Master12345",
                        null, Role.MASTER, 3.2, null, Speciality.MANICURIST));
    }

    public static List<Order> createTestOrderList() throws ParseException {
        int idIndex = 1;

        String oldString = "2022-08-20 19:30:00";
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(oldString);

        return List.of(new Order(idIndex++, createTestUser(), createTestMasterWithoutTimetable(), createTestFavor(), Status.AWAITING_PAYMENT, date, null),
                new Order(idIndex++, createTestUser(), createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.PAID, date, null),

                new Order(idIndex, null, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.FREE, date, null),

                new Order(idIndex++, createTestUser(), createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.PAID, date, null),

                new Order(idIndex, null, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.FREE, date, null),

                new Order(idIndex, null, createTestMasterWithoutTimetable(),
                        createTestFavor(), Status.FREE, date, null));
    }
}

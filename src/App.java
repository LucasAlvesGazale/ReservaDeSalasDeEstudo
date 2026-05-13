package src;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import src.classes.agenda.ReportService;
import src.classes.agenda.UserNotifier;
import src.classes.classrooms.Classroom;
import src.classes.reservation.Reservation;
import src.classes.user.User;
import src.factorys.ClassroomFactory;
import src.factorys.ClassroomFactory.ClassType;
import src.policies.FirstComeFirstServedPolicy;
import src.policies.ProfessorPriorityPolicy;
import src.policies.ReservationPolicy;

public class App {

    private final Scanner scanner = new Scanner(System.in);
    private final List<User> users = new ArrayList<>();
    private final List<Classroom> classrooms = new ArrayList<>();
    private final ReportService reportService = new ReportService();
    private final Map<Integer, UserNotifier> notifiers = new HashMap<>();
    private final Map<Integer, Set<Integer>> notifierAttachments = new HashMap<>();

    private int nextUserId = 1;
    private int nextClassroomId = 1;

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        seedSampleData();
        System.out.println("=== Study Room Reservation System ===");
        boolean running = true;
        while (running) {
            try {
                printMenu();
                String option = readLine("Choose an option: ").trim().toLowerCase();
                switch (option) {
                    case "1":  addUser();              break;
                    case "2":  listUsers();            break;
                    case "3":  addClassroom();         break;
                    case "4":  listClassrooms();       break;
                    case "5":  makeReservation();      break;
                    case "6":  cancelReservation();    break;
                    case "7":  modifyReservation();    break;
                    case "8":  listReservations();     break;
                    case "9":  changePolicy();         break;
                    case "10": reportService.printReport(); break;
                    case "0":
                    case "exit":
                    case "quit":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage());
            }
        }
        System.out.println("Bye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("---- Menu ----");
        System.out.println(" 1) Add user");
        System.out.println(" 2) List users");
        System.out.println(" 3) Add classroom");
        System.out.println(" 4) List classrooms");
        System.out.println(" 5) Make reservation");
        System.out.println(" 6) Cancel reservation");
        System.out.println(" 7) Modify reservation");
        System.out.println(" 8) List reservations of a classroom");
        System.out.println(" 9) Change classroom reservation policy");
        System.out.println("10) Print reservation report");
        System.out.println(" 0) Exit");
    }

    // ---------- Users ----------
    private void addUser() {
        String name     = readLine("Name: ").trim();
        String role     = readLine("Role (student/professor): ").trim();
        String document = readLine("Document: ").trim();
        String email    = readLine("Email: ").trim();
        User user = new User(nextUserId++, name, role, document, email);
        users.add(user);
        notifiers.put(user.getId(), new UserNotifier(user));
        notifierAttachments.put(user.getId(), new HashSet<>());
        System.out.println("User created: id=" + user.getId() + " " + user.getName());
    }

    private void listUsers() {
        if (users.isEmpty()) { System.out.println("(no users)"); return; }
        for (User u : users) {
            System.out.println(" - [" + u.getId() + "] " + u.getName()
                + " (" + u.getRole() + ") <" + u.getEmail() + ">");
        }
    }

    // ---------- Classrooms ----------
    private void addClassroom() {
        ClassType type = readClassType("Type (LAB/STUDY/LECTURE): ");
        int capacity = readInt("Capacity: ");
        ReservationPolicy policy = readPolicy(
            "Policy (1=FirstComeFirstServed, 2=ProfessorPriority): ");
        Classroom classroom = ClassroomFactory.create(type, nextClassroomId++, capacity);
        classroom.setReservationPolicy(policy);
        classroom.addObserver(reportService);
        classrooms.add(classroom);
        System.out.println("Classroom created: id=" + classroom.getId()
            + " " + classroom.getClass().getSimpleName()
            + " capacity=" + classroom.getCapacity());
    }

    private void listClassrooms() {
        if (classrooms.isEmpty()) { System.out.println("(no classrooms)"); return; }
        for (Classroom c : classrooms) {
            System.out.println(" - [" + c.getId() + "] " + c.getClass().getSimpleName()
                + " capacity=" + c.getCapacity()
                + " reservations=" + c.getReservations().size());
        }
    }

    // ---------- Reservations ----------
    private void makeReservation() {
        listUsers();
        User user = findUser(readInt("User id: "));
        listClassrooms();
        Classroom classroom = findClassroom(readInt("Classroom id: "));
        Reservation reservation = readReservation();
        attachNotifier(user, classroom);
        classroom.reserve(reservation, user);
        System.out.println("Reservation confirmed.");
    }

    private void cancelReservation() {
        listClassrooms();
        Classroom classroom = findClassroom(readInt("Classroom id: "));
        Reservation target = pickReservation(classroom);
        if (target == null) return;
        classroom.cancelReservation(target);
        System.out.println("Reservation cancelled.");
    }

    private void modifyReservation() {
        listClassrooms();
        Classroom classroom = findClassroom(readInt("Classroom id: "));
        Reservation oldRes = pickReservation(classroom);
        if (oldRes == null) return;
        listUsers();
        User user = findUser(readInt("Requesting user id: "));
        System.out.println("Enter the new reservation details:");
        Reservation newRes = readReservation();
        attachNotifier(user, classroom);
        classroom.modifyReservation(oldRes, newRes, user);
        System.out.println("Reservation modified.");
    }

    private void listReservations() {
        listClassrooms();
        Classroom classroom = findClassroom(readInt("Classroom id: "));
        List<Reservation> rs = classroom.getReservations();
        if (rs.isEmpty()) { System.out.println("(no reservations)"); return; }
        for (int i = 0; i < rs.size(); i++) {
            System.out.println(" [" + i + "] " + rs.get(i));
        }
    }

    // ---------- Policy ----------
    private void changePolicy() {
        listClassrooms();
        Classroom classroom = findClassroom(readInt("Classroom id: "));
        ReservationPolicy policy = readPolicy(
            "Policy (1=FirstComeFirstServed, 2=ProfessorPriority): ");
        classroom.setReservationPolicy(policy);
        System.out.println("Policy updated for classroom " + classroom.getId() + ".");
    }

    // ---------- Helpers ----------
    private Reservation readReservation() {
        DayOfWeek day = readDay("Day of week (1=Mon ... 7=Sun, or name): ");
        LocalTime start = readTime("Start time (HH:mm): ");
        LocalTime end = readTime("End time (HH:mm): ");
        return new Reservation(day, start, end);
    }

    private Reservation pickReservation(Classroom classroom) {
        List<Reservation> rs = classroom.getReservations();
        if (rs.isEmpty()) {
            System.out.println("Classroom has no reservations.");
            return null;
        }
        for (int i = 0; i < rs.size(); i++) {
            System.out.println(" [" + i + "] " + rs.get(i));
        }
        int idx = readInt("Pick reservation index: ");
        if (idx < 0 || idx >= rs.size()) {
            throw new IllegalArgumentException("Index out of range.");
        }
        return rs.get(idx);
    }

    private void attachNotifier(User user, Classroom classroom) {
        Set<Integer> attached = notifierAttachments
            .computeIfAbsent(user.getId(), k -> new HashSet<>());
        UserNotifier notifier = notifiers
            .computeIfAbsent(user.getId(), k -> new UserNotifier(user));
        if (attached.add(classroom.getId())) {
            classroom.addObserver(notifier);
        }
    }

    private User findUser(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    private Classroom findClassroom(int id) {
        return classrooms.stream().filter(c -> c.getId() == id).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Classroom not found: " + id));
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt).trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("  please enter an integer."); }
        }
    }

    private LocalTime readTime(String prompt) {
        while (true) {
            String s = readLine(prompt).trim();
            try { return LocalTime.parse(s); }
            catch (DateTimeParseException e) { System.out.println("  invalid time, use HH:mm."); }
        }
    }

    private DayOfWeek readDay(String prompt) {
        while (true) {
            String raw = readLine(prompt).trim().toUpperCase();
            try {
                int n = Integer.parseInt(raw);
                try { return DayOfWeek.of(n); }
                catch (java.time.DateTimeException e) { System.out.println("  number must be 1..7."); continue; }
            } catch (NumberFormatException ignored) { /* try name */ }
            try { return DayOfWeek.valueOf(raw); }
            catch (IllegalArgumentException e) { System.out.println("  invalid day."); }
        }
    }

    private ClassType readClassType(String prompt) {
        while (true) {
            String s = readLine(prompt).trim().toUpperCase();
            try { return ClassType.valueOf(s); }
            catch (IllegalArgumentException e) { System.out.println("  invalid type."); }
        }
    }

    private ReservationPolicy readPolicy(String prompt) {
        while (true) {
            String s = readLine(prompt).trim();
            if (s.equals("1")) return new FirstComeFirstServedPolicy();
            if (s.equals("2")) return new ProfessorPriorityPolicy();
            System.out.println("  invalid policy.");
        }
    }

    private void seedSampleData() {
        User alice = new User(nextUserId++, "Alice", "student",   "111", "alice@example.com");
        User bob   = new User(nextUserId++, "Bob",   "professor", "222", "bob@example.com");
        users.add(alice);
        users.add(bob);
        notifiers.put(alice.getId(), new UserNotifier(alice));
        notifiers.put(bob.getId(),   new UserNotifier(bob));
        notifierAttachments.put(alice.getId(), new HashSet<>());
        notifierAttachments.put(bob.getId(),   new HashSet<>());

        Classroom lab     = ClassroomFactory.create(ClassType.LAB,     nextClassroomId++, 20);
        Classroom study   = ClassroomFactory.create(ClassType.STUDY,   nextClassroomId++, 6);
        Classroom lecture = ClassroomFactory.create(ClassType.LECTURE, nextClassroomId++, 60);
        lecture.setReservationPolicy(new ProfessorPriorityPolicy());
        for (Classroom c : new Classroom[]{ lab, study, lecture }) {
            c.addObserver(reportService);
            classrooms.add(c);
        }
    }
}
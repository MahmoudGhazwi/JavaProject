import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static List<Advertisement> allAds = new ArrayList<>();
    private static List<Transaction> allTransactions = new ArrayList<>();

    private static List<RegisteredUser> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();

    // Auto ID counter for advertisements: A1, A2, A3, ...
    private static int adCounter = 1;

    public static void main(String[] args) {

        // Sample users
        RegisteredUser seller = new RegisteredUser("U1", "seller123", "pass1");
        RegisteredUser buyer  = new RegisteredUser("U2", "buyer123", "pass2");
        Admin admin           = new Admin("A1", "admin", "admin123");

        users.add(seller);
        users.add(buyer);
        admins.add(admin);

        System.out.println("=== Simple Marketplace System ===");

        while (true) {
            User currentUser = login();
            if (currentUser == null) {
                System.out.println("Too many failed attempts. Exiting program...");
                break;
            }

            if (currentUser instanceof Admin) {
                runAdminMenu((Admin) currentUser);
            } else if (currentUser instanceof RegisteredUser) {
                runUserMenu((RegisteredUser) currentUser);
            }
        }

        scanner.close();
    }

    // ---------- LOGIN BY ID + PASSWORD USING User.login() ----------

    private static User login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("\n=== Login ===");
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Check registered users
            for (RegisteredUser user : users) {
                if (user.login(id, password)) {
                    System.out.println("Logged in as Registered User: " + user.getUsername());
                    return user;
                }
            }

            // Check admins
            for (Admin admin : admins) {
                if (admin.login(id, password)) {
                    System.out.println("Logged in as Admin: " + admin.getUsername());
                    return admin;
                }
            }

            System.out.println("Invalid ID or password. Try again.");
            attempts++;
        }

        return null; // failed login
    }

    // ---------- USER MENU (RegisteredUser) ----------

    private static void runUserMenu(RegisteredUser currentUser) {
        int choice;

        do {
            System.out.println("\n===== User Menu =====");
            System.out.println("Logged in as: " + currentUser.getUsername());
            System.out.println("1) Post a new advertisement");
            System.out.println("2) View available advertisements");
            System.out.println("3) Purchase an advertisement");
            System.out.println("0) Logout");
            System.out.println("=====================");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    postNewAd(currentUser);
                    break;
                case 2:
                    listAvailableAdsForUser();
                    break;
                case 3:
                    PurchaseAd(currentUser);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);
    }

    // ---------- ADMIN MENU ----------

    private static void runAdminMenu(Admin admin) {
        int choice;

        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("Logged in as: " + admin.getUsername());
            System.out.println("1) View all advertisements");
            System.out.println("2) View unapproved advertisements");
            System.out.println("3) Approve an advertisement");
            System.out.println("0) Logout");
            System.out.println("======================");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    listAllAds();
                    break;
                case 2:
                    admin.viewUnapprovedAds(allAds);
                    break;
                case 3:
                    adminApproveAd(admin);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);
    }

    // ---------- USER OPERATIONS ----------

    private static void postNewAd(RegisteredUser owner) {
        System.out.println("\n=== Post a New Advertisement ===");

        // Auto-generate ID: A1, A2, A3, ...
        String id = "A" + adCounter++;
        System.out.println("Generated Ad ID: " + id);

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = readDouble();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        Advertisement ad = new Advertisement(id, title, description, price, category, owner);
        owner.postAd(ad);
        allAds.add(ad);

        System.out.println("Ad created and pending admin approval.");
    }

    // User sees only approved and not sold ads
    private static void listAvailableAdsForUser() {
        System.out.println("\n=== Available Advertisements ===");

        boolean found = false;

        for (Advertisement ad : allAds) {
            if (ad.isApproved() && !ad.isSold()) {
                ad.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available advertisements to display.");
        }
    }

    private static void PurchaseAd(RegisteredUser buyer) {
        System.out.println("\n=== Purchase Advertisement ===");

        System.out.print("Enter Ad ID to purchase: ");
        String adId = scanner.nextLine();

        Advertisement ad = findAdById(adId);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (!ad.isApproved()) {
            System.out.println("Cannot purchase an unapproved ad.");
            return;
        }

        if (ad.isSold()) {
            System.out.println("This ad has already been sold.");
            return;
        }

        Transaction t = new Transaction(
                "T" + (allTransactions.size() + 1),
                buyer,
                ad.getOwner(),
                ad,
                ad.getPrice()
        );

        t.completeTransaction();
        allTransactions.add(t);

        System.out.println("Transaction created: " + t.getId());
    }

    // ---------- ADMIN OPERATIONS ----------

    private static void adminApproveAd(Admin admin) {
        System.out.println("\n=== Approve Advertisement ===");

        System.out.print("Enter Ad ID to approve: ");
        String adId = scanner.nextLine();

        Advertisement ad = findAdById(adId);

        if (ad == null) {
            System.out.println("Ad not found.");
            return;
        }

        if (ad.isApproved()) {
            System.out.println("This ad is already approved.");
        } else {
            admin.approveAd(ad);
        }
    }

    // ---------- SHARED HELPERS ----------

    private static void listAllAds() {
        System.out.println("\n=== All Advertisements (Admin View) ===");

        if (allAds.isEmpty()) {
            System.out.println("No advertisements available.");
            return;
        }

        for (Advertisement ad : allAds) {
            ad.displayDetails();
        }
    }

    private static Advertisement findAdById(String id) {
        for (Advertisement ad : allAds) {
            if (ad.getId().equals(id)) {
                return ad;
            }
        }
        return null;
    }

    private static double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return val;
    }
}

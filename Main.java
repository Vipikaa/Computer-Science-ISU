//NAME: Vipikaa Sivakumar
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        System.out.println("Welcome to Your Local Service Marketplace!");
        ArrayList<String> serviceNames = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> hourlyRates = new ArrayList<>();
        ArrayList<Boolean> serviceAvailability = new ArrayList<>();
        ArrayList<Integer> cart = new ArrayList<>();
        ArrayList<String> cartServiceNames = new ArrayList<>();
        ArrayList<Double> cartHourlyRates = new ArrayList<>();
        while (option != 4) {
            System.out.println("Choose an option by entering its corresponding number:");
            System.out.println("1. Add a Service\n2. Book a Service\n3. Check Cart\n4. Exit");//Prompt user to choose an option
            option = sc.nextInt();
            while (option < 1 || option > 4) {
                System.out.println("Invalid option, enter again");
                option = sc.nextInt();
            }
            sc.nextLine();
            switch (option) {
                case 1:
                    boolean addService = true;//Used for choosing either to keep adding services or stop
                    while (addService) {//Adding a service
                        System.out.println("Enter Category (ex: Plumbing, Cleaning): ");
                        categories.add(sc.nextLine().toLowerCase());
                        System.out.println("Enter your Service Name:");
                        serviceNames.add(sc.nextLine());
                        System.out.println("Enter Hourly Rate (ex: 17.60, 24.50)");
                        hourlyRates.add(Double.parseDouble(sc.nextLine()));
                        serviceAvailability.add(true);//Automatically the service added is available
                        System.out.println("Service added successfully!");//Confirm the addition of a service
                        System.out.println("Do you want to add another service? (yes/no): ");//Ask if another service needs to be added
                        String another = sc.nextLine();
                        if (!another.equalsIgnoreCase("yes")) {
                            addService = false;
                        }
                    }
                    break;
                case 2:
                    if (categories.size() == 0) {
                        System.out.println("No categories available");//No services added yet
                        break;
                    } else {
                        ArrayList<Integer> filteredIndexes = new ArrayList<>();
                        ArrayList<String> noRepeatCategories = new ArrayList<>();//To print out no duplicates of a category; more user-friendly
                        for (String cat : categories) {
                            if (!noRepeatCategories.contains(cat.toLowerCase())) {
                                noRepeatCategories.add(cat.toLowerCase());
                            }
                        }
                        String category = "";
                        boolean correctCategory = false; // Ask user for category until valid one is entered
                        while (!correctCategory) {
                            while (!(filteredIndexes.size() ==0)) {
                                filteredIndexes.remove(0);
                            }//Need to reset each time because you need to remove the previously stored indexes from filteredIndexes (those belong to the previous category).
                            // Search through the full categories list again, and collect only the indexes where the category matches the new one.
                            System.out.println("Available categories:");
                            for (String cat : noRepeatCategories) {
                                System.out.println("- " + cat);
                            }
                            System.out.println("Enter the category you want to book: ");
                            category = sc.nextLine();
                            boolean found = false;
                            for (int i = 0; i < categories.size(); i++) {
                                if (categories.get(i).equalsIgnoreCase(category)) {//Checking if the category inputted matches the category in the arraylist
                                    found = true;//Found the category; used later on for if the category isn't found
                                    correctCategory = true;
                                    filteredIndexes.add(i);
                                }
                            }
                            for (int j = 0; j < filteredIndexes.size(); j++) {
                                int index = filteredIndexes.get(j);
                                String status = "";
                                if (serviceAvailability.get(index)) {
                                    status = "Available";
                                } else {
                                    status = "Fully Booked";
                                }
                                System.out.println("[" + (j + 1) + "] " + serviceNames.get(index) + " - $" + hourlyRates.get(index) + " (" + status + ")");// Show services from the chosen category with availability and rates
                            }
                            boolean allBooked = true;
                            for (int index : filteredIndexes) {
                                if (serviceAvailability.get(index)) {
                                    allBooked = false;
                                    break;
                                }
                            }
                            if (allBooked) {
                                System.out.println("Sorry, all services in this category are fully booked.");
                                break;
                            }
                            boolean correctService = false;
                            while (!correctService) {
                                if (!found) {
                                    System.out.println("Invalid category. Try again.");
                                } else {
                                    System.out.println("Enter the number of the service you want to book: ");
                                    int service = sc.nextInt();
                                    sc.nextLine();
                                    if (service < 1 || service > filteredIndexes.size()) {
                                        System.out.println("Invalid service number, enter again.");
                                    } else {
                                        int realIndex = filteredIndexes.get(service - 1);
                                        System.out.println("Service booked successfully and added to cart!");
                                        cart.add(realIndex);
                                        cartServiceNames.add(serviceNames.get(realIndex));
                                        cartHourlyRates.add(hourlyRates.get(realIndex));
                                        serviceAvailability.set(realIndex, false); // Mark as booked
                                        correctService = true;
                                        System.out.println("Updated Cart:");
                                        for (int k = 0; k < cartServiceNames.size(); k++) {
                                            System.out.println("[" + (k + 1) + "] " + cartServiceNames.get(k) + " - $" + cartHourlyRates.get(k));//Print updated cart after each addition of a service
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if (cart.size() == 0) {
                        System.out.println("Your cart is empty");
                    } else {
                        boolean cartMenu = true;
                        while (cartMenu) {
                            System.out.println("Your Cart:");
                            for (int i = 0; i < cartServiceNames.size(); i++) {
                                System.out.println("[" + (i + 1) + "] " + cartServiceNames.get(i) + " - $" + cartHourlyRates.get(i));
                            }
                            System.out.println("Options:");
                            System.out.println("1. Remove a service\n2. Update a service\n3. Checkout\n4. Return to Main Menu");
                            int cartOption = sc.nextInt();//Allow user to input a number for their desired option
                            sc.nextLine();
                            switch (cartOption) {
                                case 1:
                                    System.out.println("Enter the number of the service to remove:");
                                    int removeIndex = sc.nextInt() - 1;
                                    sc.nextLine();
                                    if (removeIndex >= 0 && removeIndex < cart.size()) {
                                        int serviceIndex = cart.get(removeIndex) - 1;
                                        serviceAvailability.set(serviceIndex, true); // Mark as available again
                                        cart.remove(removeIndex);//Remove the service from the cart, cartServiceNames, and cartHourlyRates
                                        cartServiceNames.remove(removeIndex);
                                        cartHourlyRates.remove(removeIndex);
                                        System.out.println("Service removed from cart.");
                                    } else {
                                        System.out.println("Invalid index.");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the number of the service to update:");
                                    int updateIndex = sc.nextInt() - 1;
                                    sc.nextLine();
                                    if (updateIndex >= 0 && updateIndex < cart.size()) {
                                        int currentServiceIndex = cart.get(updateIndex);
                                        serviceAvailability.set(currentServiceIndex, true); // Make the old service available again
                                        // Let user re-book a new service
                                        System.out.println("Please rebook a new service from the main menu.");
                                        cart.remove(updateIndex);
                                        cartServiceNames.remove(updateIndex);
                                        cartHourlyRates.remove(updateIndex);
                                        cartMenu = false;
                                        option = 2; // Go to booking a service option
                                    } else {
                                        System.out.println("Invalid index.");
                                    }
                                    break;
                                case 3:
                                    double total = 0;
                                    System.out.println("Invoice:");
                                    for (int i = 0; i < cartServiceNames.size(); i++) {
                                        System.out.println(cartServiceNames.get(i) + " - $" + cartHourlyRates.get(i));
                                        total += cartHourlyRates.get(i);//Add up all the service's hourly rate and print out an invoice
                                    }
                                    System.out.printf("Total: $%.2f\n", total);
                                    System.out.println("Order confirmed. Thank you for using the Local Service Marketplace!");
                                    while (!(cart.size() ==0)) {//Empty cart, cartServiceNames adn cartHourlyRates because the user checked out
                                        cart.remove(0);
                                    }
                                    while (!(cartServiceNames.size() ==0)) {
                                        cartServiceNames.remove(0);
                                    }
                                    while (!(cartHourlyRates.size() ==0)) {
                                        cartHourlyRates.remove(0);
                                    }
                                    break;
                                case 4:
                                    cartMenu = false;
                                    break;
                            }
                        }
                    }
            }
            if (option == 4) {
                System.out.println("Thank you for visiting your Local Marketplace!");
            }
        }
        sc.close();
    }
}
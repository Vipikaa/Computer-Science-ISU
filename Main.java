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
            sc.nextLine();
            while (option < 1 || option > 4) {
                System.out.println("Invalid option, enter again");
                option = sc.nextInt();
                sc.nextLine();
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
                        ArrayList<Integer> filteredIndexes = new ArrayList<>();//Stores the indexes of services that match the user's chosen category
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
                                if (categories.get(i).equalsIgnoreCase(category)) {
                                    found = true;
                                    filteredIndexes.add(i);//Finds indexes of services that match the user's chosen category and adds them to its arraylist
                                }
                            }
                            if (!found) {//If the category isn't found
                                System.out.println("Invalid category. Try again.");
                                option=2;//Goes back to booking a service option
                            }
                            boolean allBooked = true;//Checks if all services in the category are already booked
                            for (int index : filteredIndexes) {
                                if (serviceAvailability.get(index)) {
                                    allBooked = false;
                                    break;
                                }
                            }
                            if (allBooked) {//If all are booked, it brings user back to main menu
                                System.out.println("Sorry, all services in this category are fully booked.");
                                break;
                            }
                            correctCategory = true; //Mean that the user has successfully entered a valid category
                            for (int j = 0; j < filteredIndexes.size(); j++) {
                                int index = filteredIndexes.get(j);//The actual index of the service in the whole list
                                String status="";
                                if (serviceAvailability.get(index)) {//Checking if the service at that index is available or not
                                    status = "Available";
                                } else {
                                    status = "Fully Booked";
                                }
                                System.out.println("[" + (j + 1) + "] " + serviceNames.get(index) + " - $" + hourlyRates.get(index) + " (" + status + ")");
                            }
                            boolean correctService = false;//Check whether the user selected a valid service
                            while (!correctService) {
                                if (!found) {
                                    System.out.println("Invalid category. Try again.");
                                } else {
                                    System.out.println("Enter the number of the service you want to book: ");
                                    int service = sc.nextInt();
                                    sc.nextLine();
                                    if (service < 1 || service > filteredIndexes.size()) {//Checks whether the number they chose for the service is within the valid range of starting from 1 for the user, but internally they’re indexed from 0 — that's why the valid range is from 1 to filteredIndexes.size().
                                        System.out.println("Invalid service number, enter again.");
                                    } else {
                                        int realIndex = filteredIndexes.get(service - 1);//Perform service-1 for real index of the service
                                        System.out.println("Service booked successfully and added to cart!");
                                        cart.add(realIndex);//Add the service and hourly rates accordingly to their arraylist
                                        cartServiceNames.add(serviceNames.get(realIndex));
                                        cartHourlyRates.add(hourlyRates.get(realIndex));
                                        serviceAvailability.set(realIndex, false); // Mark as booked
                                        correctService = true;//Means a valid service was inputted
                                        System.out.println("Updated Cart:");
                                        for (int k = 0; k < cartServiceNames.size(); k++) {
                                            System.out.println("[" + (k + 1) + "] " + cartServiceNames.get(k) + " - $" + cartHourlyRates.get(k));//Print updated cart after each addition of a service
                                        }//Print out the updated cart
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
                        boolean cartMenu = true;//Used for the cart menu
                        while (cartMenu) {
                            System.out.println("Your Cart:");
                            for (int i = 0; i < cartServiceNames.size(); i++) {
                                System.out.println("[" + (i + 1) + "] " + cartServiceNames.get(i) + " - $" + cartHourlyRates.get(i));
                            }//Show what services are in the cart currently
                            System.out.println("Options:");
                            System.out.println("1. Remove a service\n2. Update a service\n3. Checkout\n4. Return to Main Menu");
                            int cartOption = sc.nextInt();//Allow user to input a number for their desired option
                            sc.nextLine();
                            switch (cartOption) {
                                case 1:
                                    System.out.println("Enter the number of the service to remove:");
                                    int removeIndex = sc.nextInt() - 1;//Converts to real index to remove
                                    sc.nextLine();
                                    if (removeIndex >= 0 && removeIndex < cart.size()) {//Checking if the index is within valid range of the cart
                                        int serviceIndex = cart.get(removeIndex);//Get the original index of the service from the cart
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
                                    int updateIndex = sc.nextInt() - 1;//Converts to real index to update
                                    sc.nextLine();
                                    if (updateIndex >= 0 && updateIndex < cart.size()) {//Checks if the index is valid within its range
                                        int currentServiceIndex = cart.get(updateIndex);//Get the original index of the booked service
                                        serviceAvailability.set(currentServiceIndex, true); // Make the old service available again
                                        // Let user re-book a new service
                                        System.out.println("Please rebook a new service from the main menu.");
                                        cart.remove(updateIndex);//Removes it from all cart related arraylists
                                        cartServiceNames.remove(updateIndex);
                                        cartHourlyRates.remove(updateIndex);
                                        cartMenu = false;//User is done with the cart menu
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
                                    while (!(cart.size() ==0)) {//Empty cart, cartServiceNames and cartHourlyRates because the user checked out
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
                                    cartMenu = false;//Back to main menu
                                    break;
                            }
                        }
                    }
            }
            if (option == 4) {//Exit option
                System.out.println("Thank you for visiting your Local Marketplace!");
            }
        }
        sc.close();
    }
}

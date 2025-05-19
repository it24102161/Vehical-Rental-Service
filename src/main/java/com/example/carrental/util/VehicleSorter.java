// src/main/java/com/example/carrental/util/VehicleSorter.java
package com.example.carrental.util;

import com.example.carrental.model.Vehicle;
import java.util.List;

public class VehicleSorter {

    // Selection sort implementation for sorting vehicles by daily rate (price)
    public static void sortByPrice(List<Vehicle> vehicles, boolean ascending) {
        int n = vehicles.size();

        for (int i = 0; i < n - 1; i++) {
            int minMaxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    // Finding the minimum element for ascending order
                    if (vehicles.get(j).getDailyRate() < vehicles.get(minMaxIndex).getDailyRate()) {
                        minMaxIndex = j;
                    }
                } else {
                    // Finding the maximum element for descending order
                    if (vehicles.get(j).getDailyRate() > vehicles.get(minMaxIndex).getDailyRate()) {
                        minMaxIndex = j;
                    }
                }
            }

            // Swap the found minimum/maximum element with the element at index i
            if (minMaxIndex != i) {
                Vehicle temp = vehicles.get(i);
                vehicles.set(i, vehicles.get(minMaxIndex));
                vehicles.set(minMaxIndex, temp);
            }
        }
    }

    // Selection sort implementation for sorting vehicles by availability
    public static void sortByAvailability(List<Vehicle> vehicles, boolean availableFirst) {
        int n = vehicles.size();

        for (int i = 0; i < n - 1; i++) {
            int selectedIndex = i;

            for (int j = i + 1; j < n; j++) {
                boolean currentIsAvailable = vehicles.get(selectedIndex).isAvailable();
                boolean nextIsAvailable = vehicles.get(j).isAvailable();

                if (availableFirst) {
                    // If we want available vehicles first
                    if (!currentIsAvailable && nextIsAvailable) {
                        selectedIndex = j;
                    }
                } else {
                    // If we want unavailable vehicles first
                    if (currentIsAvailable && !nextIsAvailable) {
                        selectedIndex = j;
                    }
                }
            }

            // Swap the selected element with the element at index i
            if (selectedIndex != i) {
                Vehicle temp = vehicles.get(i);
                vehicles.set(i, vehicles.get(selectedIndex));
                vehicles.set(selectedIndex, temp);
            }
        }
    }

    // Combined sort: first by availability, then by price
    public static void sortByAvailabilityAndPrice(List<Vehicle> vehicles, boolean availableFirst, boolean ascendingPrice) {
        int n = vehicles.size();

        // First, group by availability
        sortByAvailability(vehicles, availableFirst);

        // Then sort each group by price
        // First, find the boundary between available and unavailable vehicles
        int availableBoundary = 0;
        for (int i = 0; i < n; i++) {
            if (vehicles.get(i).isAvailable() == availableFirst) {
                availableBoundary++;
            } else {
                break;
            }
        }

        // Sort the available group
        selectionSortByPriceSubList(vehicles, 0, availableBoundary - 1, ascendingPrice);

        // Sort the unavailable group
        selectionSortByPriceSubList(vehicles, availableBoundary, n - 1, ascendingPrice);
    }

    // Helper method to sort a sublist of vehicles by price using selection sort
    private static void selectionSortByPriceSubList(List<Vehicle> vehicles, int start, int end, boolean ascending) {
        if (start >= end) {
            return;
        }

        for (int i = start; i < end; i++) {
            int minMaxIndex = i;

            for (int j = i + 1; j <= end; j++) {
                if (ascending) {
                    if (vehicles.get(j).getDailyRate() < vehicles.get(minMaxIndex).getDailyRate()) {
                        minMaxIndex = j;
                    }
                } else {
                    if (vehicles.get(j).getDailyRate() > vehicles.get(minMaxIndex).getDailyRate()) {
                        minMaxIndex = j;
                    }
                }
            }

            if (minMaxIndex != i) {
                Vehicle temp = vehicles.get(i);
                vehicles.set(i, vehicles.get(minMaxIndex));
                vehicles.set(minMaxIndex, temp);
            }
        }
    }
}
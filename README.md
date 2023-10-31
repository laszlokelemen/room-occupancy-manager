# Room Occupancy Manager

[![Build Status](https://github.com/laszlokelemen/room-occupancy-manager/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/laszlokelemen/room-occupancy-manager)

## Description

It's a Spring Boot API that provides an interface for hotels to enter the numbers of Premium and Economy rooms that are
available for the night and then tells them immediately how many rooms of each category will be occupied and how much
money they will make in total.

## Technologies Used

- Java 17
- Spring Boot 3
- Gradle

### Prerequisites

What things you need to install before running the application:

- Java JDK 17 or higher
- Gradle (if not already installed)

### Installing

1. Clone the repository: `git clone https://github.com/laszlokelemen/room-occupancy-manager.git`
2. Change into the project directory: `cd room-occupancy-manager`
3. Build the application: `gradle build`
4. Run the application: `gradle bootRun`

The app will start running at http://localhost:8080

## API Endpoint


### Optimize

| Method | Url            | Description              | Sample Valid Request Body | Sample Valid Response Body |
|--------|----------------|--------------------------|---------------------------|:---------------------------|
| POST   | /room/optimize | Optimize room occupancy. | [JSON](#optimizeRequest)  | [JSON](#optimizeResponse)  |

##### <a href="optimizeRequest">Optimize Room Occupancy Request -> /room/optimize</a>

```json
{
  "guests": [
    23,
    45,
    155,
    374,
    22,
    99.99,
    100,
    101,
    115,
    209
  ],
  "numberOfFreeEconomyRooms": 3,
  "numberOfFreePremiumRooms": 3
}
```

##### <a id="optimizeResponse">Optimize Room Occupancy Response</a>

```json
{
  "usagePremium": 3,
  "usageEconomy": 3,
  "pricePremium": 738,
  "priceEconomy": 167.99
}
```

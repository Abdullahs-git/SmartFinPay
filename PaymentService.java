import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

public class PaymentService {

	private static final double PKR_TO_USD = 0.0059;
	private static final double PKR_TO_EUR = 0.0050;
	private static final double PKR_TO_AED = 0.0120;
	private static final double PKR_TO_SAR = 0.0120;

	static List<Donation> donations = new ArrayList<>();
	static List<Loan> loans = new ArrayList<>();
	static List<Transaction> transactions = new ArrayList<>();

	static BusTicket[] busSeats = new BusTicket[10];
	private static LinkedList<User> users = new LinkedList<>();
	private static User loggedInUser = null;
	static Scanner scanner;
	static PaymentTree tree = new PaymentTree();
	static int amount = 0, size = 1, ammount;
	static Stack<String> info = new Stack<>();
	static First[] first = new First[10];
	static char donateChoice;

	public PaymentService() {
		for (int i = 0; i < busSeats.length; i++) {
			busSeats[i] = new BusTicket("Gujranwala", "Lahore", "2023-08-20", "10:00 AM", i + 1);
			scanner = new Scanner(System.in);
		}
	}// constructor()

	public static void register() {
		System.out.println("Please enter your username: ");
		String username = scanner.next();
		System.out.println("Please enter your password: ");
		String password = scanner.next();
		System.out.println("Please enter your account number: ");
		String accountNumber = scanner.next();

		User newUser = new User(username, password, accountNumber, 0);
		users.add(newUser);
		System.out.println("Registration successful.");
	}// register()

	public static void login() {
		System.out.println("Please enter your username: ");
		String username = scanner.next();
		System.out.println("Please enter your password: ");
		String password = scanner.next();

		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				loggedInUser = user;
				System.out.println("Login successful.");
				Display();
				return;
			}
		}

		System.out.println("Invalid username or password.");
	}// login()

	public static void choice(int n) {
		switch (n) {
			case 1:
				Deposit();
				break;
			case 2:
				WITHDRAW();
				break;

			case 3:
				display1(2);
				displayTransactions();
				Display();
				break;

			case 4:
				transferFunds();
				break;

			case 5:
				System.out.println("===================Types of bills and Utilities===============");
				System.out.println("\t1. Electricity Bills \n2. Water Bills \n3. Internet Bills \n4. University Fees");
				System.out.print(" Enter Your choice = ");
				int nm = scanner.nextInt();
				switch (nm) {
					case 1:
						System.out.println("===================Types of bill===============");
						System.out.println("\t1. GEPCO \t2. HESCO \t3. FESCO \t4. Exit");
						System.out.print(" Enter Your choice = ");
						int nn = scanner.nextInt();
						if (nn == 1) {
							System.out.println("==================GEPCO-BILL=========");
							System.out.print(" Enter Bill-Id : ");
							String bill_id = scanner.next();
							System.out.print(" Enter Bill-Amount : ");
							int bill_amount = scanner.nextInt();
							System.out.print(" Enter Phone-Number : ");
							String phone_number = scanner.next();
							if (bill_amount <= amount) {
								first[size++] = new First(bill_id, phone_number, amount, "GEPCO", bill_amount,
										(amount - bill_amount));
								loggedInUser.setBalance(amount - bill_amount);
								System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
								char m = scanner.next().charAt(0);
								if (m == 'Y' || m == 'y') {
									tree.insert("GEPCO-Bill", bill_id, bill_amount);
									System.out.println(" Successfully! Saved ");
									Display();
								} else if (m == 'n' || m == 'N') {
									System.out.println(" Successfully! Cancelled ");
									size--;
									Display();
								} else {
									System.out.println("INVALID! CHOICE ");
									Display();
								}
								Display();
							} else {
								System.out.println("You have in sufficent balance");
								Display();
							}
						} else if (nn == 2) {
							System.out.println("==================HESCO-BILL=========");
							System.out.print(" Enter Bill-Id : ");
							String bill_id = scanner.next();
							System.out.print(" Enter Bill-Amount : ");
							int bill_amount = scanner.nextInt();
							System.out.print(" Enter Phone-Number : ");
							String phone_number = scanner.next();
							if (bill_amount <= amount) {
								first[size++] = new First(bill_id, phone_number, amount, "HESCO", bill_amount,
										(amount - bill_amount));
								loggedInUser.setBalance(amount - bill_amount);
								display1(size);
								Display();
								System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
								char m = scanner.next().charAt(0);
								if (m == 'Y' || m == 'y') {
									tree.insert("HESCO-Bill", bill_id, bill_amount);
									System.out.println(" Successfully! Saved ");
									Display();
								} else if (m == 'n' || m == 'N') {
									System.out.println(" Successfully! Cancelled ");
									size--;
									Display();
								} else {
									System.out.println("INVALID! CHOICE ");
								}
							} else {
								System.out.println("You have in sufficent balance");
							}
						} else if (nn == 3) {
							System.out.println("==================FESCO-BILL=========");
							System.out.print(" Enter Bill-Id : ");
							String bill_id = scanner.next();
							System.out.print(" Enter Bill-Amount : ");
							int bill_amount = scanner.nextInt();
							System.out.print(" Enter Phone-Number : ");
							String phone_number = scanner.next();
							tree.insert("GEPCO-Bill", bill_id, bill_amount);
							if (bill_amount <= amount) {
								first[size++] = new First(bill_id, phone_number, amount, "FESCO", bill_amount,
										(amount - bill_amount));
								loggedInUser.setBalance(amount - bill_amount);
								display1(size);
								System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
								char m = scanner.next().charAt(0);
								if (m == 'Y' || m == 'y') {
									tree.insert("FESCO-Bill", bill_id, bill_amount);
									System.out.println(" Successfully! Saved ");
									Display();
								} else if (m == 'n' || m == 'N') {
									System.out.println(" Successfully! Cancelled ");
									size--;
									Display();
								} else {
									System.out.println("INVALID! CHOICE ");
									Display();
								}
								Display();
							} else {
								System.out.println("You have in sufficent balance");
								Display();
							}
						} else if (nn == 4) {
							System.exit(0);
						} else {
							System.out.println("\t INVALID! Choice ");
							Display();
						}
						break;
					case 2:
						System.out.println("==================Water-BILL=========");
						System.out.print(" Enter Bill-Id : ");
						String bill_id = scanner.next();
						System.out.print(" Enter Bill-Amount : ");
						int bill_amount = scanner.nextInt();
						System.out.print(" Enter Phone-Number : ");
						String phone_number = scanner.next();
						tree.insert("Water-Bill", bill_id, bill_amount);
						if (bill_amount <= amount) {
							first[size++] = new First(bill_id, phone_number, amount, "Water", bill_amount,
									(amount - bill_amount));
							display1(size);
							System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
							char m = scanner.next().charAt(0);
							if (m == 'Y' || m == 'y') {
								tree.insert("Water-Bill", bill_id, bill_amount);
								System.out.println(" Successfully! Saved ");
								Display();
							} else if (m == 'n' || m == 'N') {
								System.out.println(" Successfully! Cancelled ");
								size--;
								Display();
							} else {
								System.out.println("INVALID! CHOICE ");
								Display();
							}
							Display();
						} else {
							System.out.println("You have in sufficent balance");
							Display();
						}
						break;
					case 3:
						System.out.println("==================Internet-BILL=========");
						System.out.print(" Enter Bill-Id : ");
						String bill_id1 = scanner.next();
						System.out.print(" Enter Bill-Amount : ");
						int bill_amount1 = scanner.nextInt();
						System.out.print(" Enter Phone-Number : ");
						String phone_number1 = scanner.next();
						if (bill_amount1 <= amount) {
							first[size++] = new First(bill_id1, phone_number1, amount, "Internet", bill_amount1,
									(amount - bill_amount1));
							display1(size);
							System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
							char m = scanner.next().charAt(0);
							if (m == 'Y' || m == 'y') {
								tree.insert("Internet-Bill", bill_id1, bill_amount1);
								System.out.println(" Successfully! Saved ");
								Display();
							} else if (m == 'n' || m == 'N') {
								System.out.println(" Successfully! Cancelled ");
								size--;
								Display();
							} else {
								System.out.println("INVALID! CHOICE ");
							}
							Display();
						} else {
							System.out.println("You have in sufficent balance");
							Display();
						}
						break;
					case 4:
						System.out.println("==================University-BILL=========");
						System.out.print(" Enter Bill-Id : ");
						String bill_id2 = scanner.next();
						System.out.print(" Enter Bill-Amount : ");
						int bill_amount2 = scanner.nextInt();
						System.out.print(" Enter Phone-Number : ");
						String phone_number2 = scanner.next();
						if (bill_amount2 <= amount) {
							int nnn = amount - bill_amount2;
							first[size++] = new First(bill_id2, phone_number2, amount, "University-Fees", bill_amount2,
									(nnn));
							display1(size);
							System.out.print(" Do you want to pay bill ? Y for yes && N for No : ");
							char m = scanner.next().charAt(0);
							if (m == 'Y' || m == 'y') {
								tree.insert("University-Bill", bill_id2, bill_amount2);
								System.out.println(" Successfully! Saved ");
								Display();
							} else if (m == 'n' || m == 'N') {
								System.out.println(" Successfully! Cancelled ");
								size--;
								Display();
							} else {
								System.out.println("INVALID! CHOICE ");
							}
							Display();
						} else {
							System.out.println("You have in sufficent balance");
							Display();
						}

						break;
				}
			case 6:
				ApplyForLoan();
				break;

			case 7:
				approveLoans();
				Display();
				break;
			case 8:
				makeDonation();
				break;

			case 9:
				GuessingGame();
				break;

			case 10:
				bookBusTicket();
				break;

			case 11:
				CurrencyConversion();
				break;

			case 12:
				mobileLoad();
				break;

			case 13:
				contactSupport();
				break;

			case 14:
				prayerTimings();
				break;

			case 15:
				Reward();
				break;

			case 16:
				loggedInUser = null;
				System.out.println("Logged out.");
				return;
			default:
				System.out.println("Invalid choice.");
				Display();
				break;
		}
	}// choice()

	public static void Deposit() {

		System.out.print(" Enter Amount for Deposite : ");
		int dep = scanner.nextInt();
		System.out.print(" Enter Phone-Number : ");
		String phone = scanner.next();
		while (phone.length() < 11 || phone.length() > 11) {
			System.out.print("Invlid! Please Enter Again Phone-Number : ");
			phone = scanner.next();
		}

		System.out.print(" Enter Account-Number : ");
		String account = scanner.next();
		int a = amount;
		amount += dep;
		loggedInUser.setBalance(amount);
		first[size++] = new First(account, phone, a, "Deposit", dep, amount);
		display1(size);
		System.out.print("Do you want to donate? (Y/N): ");
		donateChoice = scanner.next().charAt(0);
		if (donateChoice == 'Y' || donateChoice == 'y') {
			makeDonation();
		}
		Display();
	}// Deposit()

	public static void WITHDRAW() {
		System.out.print("Enter Account-Number: ");
		String account1 = scanner.next();
		System.out.print("Enter Phone-Number: ");
		String phone = scanner.next();
		while (phone.length() < 11 || phone.length() > 11) {
			System.out.print("Invalid! Please Enter Again Phone-Number: ");
			phone = scanner.next();
		}
		System.out.print("Enter Amount WANT TO Withdraw: ");
		int withdrawAmount = scanner.nextInt();

		if (amount >= withdrawAmount) {
			amount -= withdrawAmount;
			loggedInUser.setBalance(amount);
			int aa = amount;
			first[size++] = new First(account1, phone, aa, "With-Draw", withdrawAmount, amount);
			display1(size);
			System.out.print("Do you want to donate? (Y/N): ");
			donateChoice = scanner.next().charAt(0);
			if (donateChoice == 'Y' || donateChoice == 'y') {
				makeDonation();
			}
			Display();
		} else {
			System.out.println("You have insufficient balance");
			display1(size);
			Display();
		}
	}// WITHDRAW()

	public static void transferFunds() {
		System.out.println("Enter recipient's account number: ");
		String recipientAccount = scanner.next();
		System.out.println("Enter amount to transfer: ");
		int transferAmount = scanner.nextInt();

		if (loggedInUser.getBalance() >= transferAmount && transferAmount > 0) {
			User recipient = findUserByAccountNumber(recipientAccount);
			if (recipient != null) {
				loggedInUser.setBalance(loggedInUser.getBalance() - transferAmount);
				recipient.setBalance(recipient.getBalance() + transferAmount);
				System.out.println("Transfer successful.");
			} else {
				System.out.println("Recipient account not found.");
			}
		} else {
			System.out.println("Invalid transfer amount or insufficient balance.");
		}
		Display();
	}
	// transferFunds()

	public static User findUserByAccountNumber(String accountNumber) {
		for (User user : users) {
			if (user.getAccountNumber().equals(accountNumber)) {
				return user;
			}
		}
		return null;
	}// findUserByAccountNumber()

	public static void contactSupport() {
		String supportPhoneNumber = "03081180346";
		String message = "Hello, I need assistance with my account.";

		try {
			String url = "https://api.whatsapp.com/send?phone=" + supportPhoneNumber + "&text="
					+ URLEncoder.encode(message, "UTF-8");
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			System.out.println("An error occurred while opening WhatsApp.");
			e.printStackTrace();
		}
	}// contactSupport()

	public static void ApplyForLoan() {
		System.out.println("==================Loan-Application=========");
		System.out.print("Enter your account number: ");
		String accountNumber = scanner.next();

		System.out.print("Enter your CNIC number: ");
		String CNICNumber = scanner.next();

		while (CNICNumber.length() != 13) {
			System.out.println("Error: Input length is not equal to 13 characters.");
			System.out.print("Enter a string (13 characters): ");
			CNICNumber = scanner.next();
		}

		System.out.print("Enter loan amount: ");
		int loanAmount = scanner.nextInt();

		Loan loan = new Loan(accountNumber, CNICNumber, loanAmount);
		loans.add(loan);

		System.out.println("Loan application submitted.");
		Display();
	}// ApplyForLoan()

	public static void approveLoans() {
		System.out.println("==================Loan-Approval=========");

		Iterator<Loan> loanIterator = loans.iterator();
		while (loanIterator.hasNext()) {
			Loan pendingLoan = loanIterator.next();

			if (!pendingLoan.isApproved()) {
				String cnicNumber = pendingLoan.getCNICNumber();
				char lastDigit = cnicNumber.charAt(cnicNumber.length() - 1);
				boolean cnicIsFemale = (lastDigit - '0') % 2 == 0;

				System.out.println("CNIC Number: " + cnicNumber);
				System.out.print("Enter your gender (male/female): ");
				String userGender = scanner.next();

				boolean isUserMale = userGender.equalsIgnoreCase("male");
				boolean isUserFemale = userGender.equalsIgnoreCase("female");

				if (isUserFemale && cnicIsFemale) {
					System.out.println("Loan approved for account: " + pendingLoan.getAccountNumber());
					pendingLoan.approveLoan();
					loggedInUser.setBalance(loggedInUser.getBalance() + pendingLoan.getLoanAmount());
					loanIterator.remove();
				} else if (isUserMale && !cnicIsFemale) {
					System.out.println("Loan approved for account: " + pendingLoan.getAccountNumber());
					pendingLoan.approveLoan();
					loggedInUser.setBalance(loggedInUser.getBalance() + pendingLoan.getLoanAmount());
					loanIterator.remove();
				} else {
					System.out.println("Loan rejected for account: " + pendingLoan.getAccountNumber() +
							" (loan approval based on gender)");
				}
			}
		}

		System.out.print("Do you want to donate? (Y/N): ");
		donateChoice = scanner.next().charAt(0);
		if (donateChoice == 'Y' || donateChoice == 'y') {
			makeDonation();
		}
	}// approveLoans()

	public static void makeDonation() {
		System.out.print("Enter the donation amount: ");
		int donationAmount = scanner.nextInt();

		if (donationAmount > 0 && donationAmount <= loggedInUser.getBalance()) {
			loggedInUser.setBalance(loggedInUser.getBalance() - donationAmount);
			donations.add(new Donation(donationAmount));
			System.out.println("Thank you for your donation!");
		} else {
			System.out.println("Invalid donation amount or insufficient balance.");
		}
		Display();
	}// make Donation()

	public static void GuessingGame() {
		int randomNumber = (int) (Math.random() * 5) + 1;

		System.out.println("Welcome to the Guessing Game!");
		System.out.print("Guess a number between 1 and 5: ");
		int userGuess = scanner.nextInt();

		if (userGuess == randomNumber) {
			System.out.println("Congratulations! You guessed the correct number.");
			loggedInUser.setBalance(loggedInUser.getBalance() + 5);
			amount += 5;
		} else {
			System.out.println("Oops! Your guess is incorrect.");
		}

		Display();
	}// GuessingGame()

	public static void bookBusTicket() {

		int ticketPrice = 500;
		if (loggedInUser.getBalance() < ticketPrice) {
			System.out.println("Sorry, your balance is insufficient to book a bus ticket.");
			Display();
			return;
		}

		System.out.println("==================Bus Ticket Booking=========");
		System.out.println("Available Buses:");
		for (int i = 0; i < busSeats.length; i++) {
			if (!busSeats[i].isBooked()) {
				System.out.println("Bus #" + (i + 1) + " - " + busSeats[i].getDepartureCity() +
						" to " + busSeats[i].getArrivalCity() + " (" + busSeats[i].getDate() +
						" at " + busSeats[i].getTime() + ")");
			}
		}

		System.out.print("Select a bus number: ");
		int selectedBus = scanner.nextInt();
		if (selectedBus < 1 || selectedBus > busSeats.length) {
			System.out.println("Invalid bus number.");
			return;
		}

		BusTicket selectedTicket = busSeats[selectedBus - 1];
		if (selectedTicket.isBooked()) {
			System.out.println("Sorry, the selected seat is already booked.");
			return;
		}

		System.out.println("Available seats for Bus #" + selectedBus + ":");
		for (int i = 0; i < busSeats.length; i++) {
			if (!busSeats[i].isBooked()) {
				System.out.print(busSeats[i].getSeatNumber() + " ");
			}
		}

		System.out.print("\nSelect a seat number: ");
		int selectedSeat = scanner.nextInt();
		if (selectedSeat < 1 || selectedSeat > busSeats.length) {
			System.out.println("Invalid seat number.");
			return;
		}

		BusTicket bookedTicket = busSeats[selectedSeat - 1];
		bookedTicket.bookTicket();
		loggedInUser.setBalance(loggedInUser.getBalance() - ticketPrice);
		transactions.add(new Transaction(loggedInUser.getAccountNumber(), "Bus Ticket", ticketPrice));

		System.out.println("Bus ticket booked successfully for seat " + bookedTicket.getSeatNumber());
	}// bookBusTicket()

	public static void CurrencyConversion() {
		System.out.println("==================PKR to Other Currencies Conversion=========");
		System.out.println("1. PKR to USD");
		System.out.println("2. PKR to EUR");
		System.out.println("3. PKR to AED");
		System.out.println("4. PKR to SAR");
		System.out.print("Select an option: ");
		int option = scanner.nextInt();

		System.out.print("Enter amount in PKR: ");
		double pkrAmount = scanner.nextDouble();
		double convertedAmount = 0.0;
		String currencyCode = "";

		switch (option) {
			case 1:
				convertedAmount = pkrAmount * PKR_TO_USD;
				currencyCode = "USD";
				break;
			case 2:
				convertedAmount = pkrAmount * PKR_TO_EUR;
				currencyCode = "EUR";
				break;
			case 3:
				convertedAmount = pkrAmount * PKR_TO_AED;
				currencyCode = "AED";
				break;
			case 4:
				convertedAmount = pkrAmount * PKR_TO_SAR;
				currencyCode = "SAR";
				break;
			default:
				System.out.println("Invalid option.");
				return;
		}

		System.out.println(pkrAmount + " PKR is equal to " + convertedAmount + " " + currencyCode);
	}// CurrencyConversion()

	public static void mobileLoad() {
		System.out.println("Select service provider: ");
		System.out.println("1. Zong\n2. Jazz\n3. Telenor");
		int serviceProviderChoice = scanner.nextInt();
		String serviceProvider;

		System.out.print("Enter the recipient's phone number: ");
		String phoneNumber = scanner.next();

		switch (serviceProviderChoice) {
			case 1:
				serviceProvider = "Zong";
				break;
			case 2:
				serviceProvider = "Jazz";
				break;
			case 3:
				serviceProvider = "Telenor";
				break;
			default:
				System.out.println("Invalid service provider choice.");
				return;
		}

		System.out.print("Enter the amount for mobile load: ");
		int loadAmount = scanner.nextInt();

		System.out.print("Are you sure you want to proceed with the mobile load? (Y/N): ");
		char confirmationChoice = scanner.next().charAt(0);

		if (confirmationChoice == 'Y' || confirmationChoice == 'y') {
			if (loadAmount <= loggedInUser.getBalance()) {
				loggedInUser.setBalance(loggedInUser.getBalance() - loadAmount);
				transactions.add(new Transaction(loggedInUser.getAccountNumber(), "Mobile Load - " + serviceProvider,
						loadAmount));
				System.out.println("Mobile load successful.");
			} else {
				System.out.println("Insufficient balance.");
			}
		} else {
			System.out.println("Mobile load cancelled.");
		}

		Display();
	}// mobileLoad()

	public static void prayerTimings() {
		System.out.println("Select city for prayer timings: ");
		System.out.println("1. Gujranwala\n2. Islamabad\n3. Karachi\n4. Lahore");
		int cityChoice = scanner.nextInt();

		String city;
		switch (cityChoice) {
			case 1:
				city = "Gujranwala";
				System.out.println("Prayer Timings for " + city + ":");
				System.out.println("Fajr: 04:11 AM");
				System.out.println("Dhuhr: 12:04 PM");
				System.out.println("Asr: 04:41 PM");
				System.out.println("Maghrib: 06:32 PM");
				System.out.println("Isha: 07:57 PM");
				break;
			case 2:
				city = "Islamabad";
				System.out.println("Prayer Timings for " + city + ":");
				System.out.println("Fajr: 04:12 AM");
				System.out.println("Dhuhr: 12:09 PM");
				System.out.println("Asr: 04:47 PM");
				System.out.println("Maghrib: 06:38 PM");
				System.out.println("Isha: 08:05 PM");
				break;
			case 3:
				city = "Karachi";
				System.out.println("Prayer Timings for " + city + ":");
				System.out.println("Fajr: 04:53 AM");
				System.out.println("Dhuhr: 12:33 PM");
				System.out.println("Asr: 05:05 PM");
				System.out.println("Maghrib: 06:54 PM");
				System.out.println("Isha: 08:13 PM");
				break;
			case 4:
				city = "Lahore";
				System.out.println("Prayer Timings for " + city + ":");
				System.out.println("Fajr: 04:11 AM");
				System.out.println("Dhuhr: 12:04 PM");
				System.out.println("Asr: 04:40 PM");
				System.out.println("Maghrib: 06:31 PM");
				System.out.println("Isha: 07:55 PM");
				break;
			default:
				System.out.println("Invalid city choice.");
				return;
		}

		Display();
	}// prayerTimings()

	public static void Reward() {
		if (loggedInUser.getBalance() < 10000) {
			System.out.println("Sorry, you need at least 10,000 in your account to play the bonus game.");
			Display();
			return;
		}

		System.out.println("Welcome to the Bonus Game: Dice Rolling!");
		System.out.println("Press Enter to roll the dice...");
		scanner.nextLine();

		int diceValue = (int) (Math.random() * 6) + 1;
		System.out.println("You rolled a " + diceValue);

		int winAmount = diceValue * 10;
		System.out.println("You've won " + winAmount + "!");

		loggedInUser.setBalance(loggedInUser.getBalance() + winAmount);

		Display();
	}// bonusGame()

	public static void displayTransactions() {
		System.out.println("Transactions:");
		for (Transaction transaction : transactions) {
			System.out.println(transaction);
		}

		System.out.println("Donations:");
		for (Donation donation : donations) {
			System.out.println(donation);
		}

		System.out.println("Loans:");
		for (Loan loan : loans) {
			System.out.println(loan);
		}
	}// displayTransactions()

	public static void Display() {

		if (loggedInUser == null) {
			System.out.println("Not logged in.");
			return;
		}

		System.out.println("\t\t============================================================");
		System.out.println("\t====================SmartFin Pay=======================");
		System.out.println("\t\t============================================================");
		System.out.println(
				"Logged in as " + loggedInUser.getUsername() + " (Account: " + loggedInUser.getAccountNumber() + ")");
		System.out.println("Current Balance: " + loggedInUser.getBalance());
		System.out.println("\t1. Deposite \n\t2. With-Draw \n\t3. Transaction information");
		System.out.println("\t4. Transfer Funds");
		System.out.println("\t5. Utilities and Bills");
		System.out.println("\t6. Apply for Loan");
		System.out.println("\t7. Process Loans");
		System.out.println("\t8. Donate");
		System.out.println("\t9. Play Guessing Game");
		System.out.println("\t10. Book Bus Ticket");
		System.out.println("\t11. Currency Conversion");
		System.out.println("\t12. Mobile Load");
		System.out.println("\t13. Contact Customer Support on WhatsApp");
		System.out.println("\t14. Prayer Timings");
		System.out.println("\t15. Reward");
		System.out.println("\t16. Exit");
		System.out.print("Enter your choice = ");
		int choice1 = scanner.nextInt();
		choice(choice1);
	}// display()

	public static void display1(int n) {
		for (int i = n - 1; i < size; i++)
			System.out.print(first[i]);
	}

	public static void main(String[] args) {
		PaymentService paymentService = new PaymentService();

		while (true) {
			System.out.println("Select options: \n 1. Register \n 2. Login \n 3. Exit");
			int choice = scanner.nextInt();

			if (choice == 1) {
				register();
			} else if (choice == 2) {
				if (loggedInUser == null) {
					login();
				} else {
					System.out.println("Already logged in as " + loggedInUser.getUsername());
				}
			} else if (choice == 3) {
				System.exit(0);
			} else {
				System.out.println("Invalid choice.");
			}
		}
	}// main
}// class

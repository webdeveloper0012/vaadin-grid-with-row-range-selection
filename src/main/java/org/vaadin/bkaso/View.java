package org.vaadin.bkaso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

/**
 * 
 * @author bharatk
 * 
 *         example for range row selection with multi row selection model
 *
 */

@Route("")
public class View extends Div {
	
	private static final long serialVersionUID = 1L;

	private Set<Person> lastSelectedRowSet = new HashSet<Person>();
	private Set<Person> selectedRowSet = new HashSet<Person>();

	private Person lastSelectedRow = null;
	private Person selectedRow = null;
	
	private static Logger logger = Logger.getLogger(View.class);

	public View() {
		List<Person> personList = new ArrayList<>();

		personList.add(new Person(100, "Bharat", "K", 68, new Address("12080", "India"), "127-942-237"));
		personList.add(new Person(101, "Peter", "Buchanan", 38, new Address("93849", "New York"), "201-793-488"));
		personList.add(new Person(102, "Samuel", "Lee", 53, new Address("86829", "New York"), "043-713-538"));
		personList.add(new Person(103, "Anton", "Ross", 37, new Address("63521", "New York"), "150-813-6462"));
		personList.add(new Person(104, "Aaron", "Atkinson", 18, new Address("25415", "Washington"), "321-679-8544"));
		personList.add(new Person(105, "Jack", "Woodward", 28, new Address("95632", "New York"), "187-338-588"));

		RowRangeGrid<Person> grid = new RowRangeGrid<>(Person.class);

		grid.setItems(personList);
		grid.setMultiSort(false);
		grid.setSelectionMode(SelectionMode.MULTI);
		
		grid.addSelectionListener(e -> {
			lastSelectedRow = selectedRow;
			lastSelectedRowSet = selectedRowSet;
			selectedRowSet = e.getAllSelectedItems();
			if (selectedRowSet.size() > lastSelectedRowSet.size()) {
				Set<Person> result = new HashSet<Person>(selectedRowSet);
				result.removeAll(lastSelectedRowSet);
				logger.info(result);
				lastSelectedRow = selectedRow;
				selectedRow = result.iterator().next();
			} else {
				lastSelectedRow = null;
				selectedRow = null;
			}
			logger.info(e.getAllSelectedItems());
		});

		grid.addKeyDownListener(Key.SHIFT,e -> {
				logger.info("Shift key pressed!");
				if (lastSelectedRow != null && selectedRow != null) {
					if (!lastSelectedRow.equals(selectedRow)) {
						ListDataProvider<Person> listDataProvider = (ListDataProvider<Person>) grid.getDataProvider();
						List<Person> items = (new ArrayList<Person>(listDataProvider.getItems()));
						List<GridSortOrder<Person>> sortOrder = grid.getSortOrder();
						if (sortOrder != null && sortOrder.size() > 0) {
							Comparator<Person> comparator = getComparator(sortOrder);
							items = items.stream().sorted(comparator).collect(Collectors.toList());
						}
						logger.info("sortedItems:" + items);

						int lastSelectedIndex = items.indexOf(lastSelectedRow);
						int selectedPersonIndex = items.indexOf(selectedRow);
						logger.info("lastSelectedIndex:" + lastSelectedIndex);
						logger.info("selectedPersonIndex:" + selectedPersonIndex);
						if (selectedPersonIndex > lastSelectedIndex) {
							for (int i = lastSelectedIndex + 1; i < selectedPersonIndex; i++) {
								grid.select(items.get(i));
							}
							grid.getDataProvider().refreshAll();
						}
					}
				}
		});

		grid.setColumns("firstName", "lastName", "age", "address", "phoneNumber");
		
		HorizontalLayout horizontal = new HorizontalLayout();		
		horizontal.add(new Label("> Press Shift+Click to select range row"));
		add(grid);
		add(horizontal);

	}

	private Comparator<Person> getComparator(List<GridSortOrder<Person>> sortOrder) {
		// Its defined with single column sorting
		GridSortOrder<Person> sortCol = sortOrder.get(0);
		return sortCol.getSorted().getComparator(sortCol.getDirection());
	}

	public static class Person implements Cloneable {
		private int id;
		private String firstName;
		private String lastName;
		private int age;
		private Address address;
		private String phoneNumber;
		private MaritalStatus maritalStatus;
		private LocalDate birthDate;
		private boolean isSubscriber;
		private String email;

		public Person() {

		}

		public Person(int id, String firstName, String lastName, int age, Address address, String phoneNumber) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.address = address;
			this.phoneNumber = phoneNumber;
		}

		public Person(int id, String firstName, String lastName, int age, Address address, String phoneNumber,
				MaritalStatus maritalStatus, LocalDate birthDate) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.address = address;
			this.phoneNumber = phoneNumber;
			this.maritalStatus = maritalStatus;
			this.birthDate = birthDate;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public MaritalStatus getMaritalStatus() {
			return maritalStatus;
		}

		public void setMaritalStatus(MaritalStatus maritalStatus) {
			this.maritalStatus = maritalStatus;
		}

		public LocalDate getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
		}

		public String getImage() {
			return getId() + ".jpg";
		}

		public boolean isSubscriber() {
			return isSubscriber;
		}

		public void setSubscriber(boolean isSubscriber) {
			this.isSubscriber = isSubscriber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Person)) {
				return false;
			}
			Person other = (Person) obj;
			return id == other.id;
		}

		@Override
		public String toString() {
			return firstName;
		}

		@Override
		public Person clone() { // NOSONAR
			try {
				return (Person) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("The Person object could not be cloned.", e);
			}
		}
	}

	public static class Address {
		private String street;
		private int number;
		private String postalCode;
		private String city;

		public Address() {

		}

		public Address(String postalCode, String city) {
			this.postalCode = postalCode;
			this.city = city;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		@Override
		public String toString() {
			return String.format("%s %s", postalCode, city);
		}

	}

	public enum MaritalStatus {
		MARRIED, SINGLE;
	}
}

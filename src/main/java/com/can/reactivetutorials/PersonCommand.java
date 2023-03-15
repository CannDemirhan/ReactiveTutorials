package com.can.reactivetutorials;

public class PersonCommand {

    private String firstName;
    private String lastName;

    public PersonCommand() {
    }

    public PersonCommand(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonCommand(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }

    public String sayName() {
        StringBuilder stringBuilder = new StringBuilder("My name is ");
        stringBuilder.append(this.getFirstName());
        stringBuilder.append(" ");
        stringBuilder.append(this.getLastName());
        stringBuilder.append(".");

        return stringBuilder.toString();
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        PersonCommand that = (PersonCommand) o;

        if(!firstName.equals(that.firstName)) return false;
        return lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonCommand{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}

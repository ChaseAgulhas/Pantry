package com.chase.apps.pantry.domain.customer;

import java.io.Serializable;

/**
 * Created by Chase on 2016-09-02.
 */
public class Customer implements Serializable {

    private String name, surname;
    private String dateOfBirth;
    private String emailAddress;
    private String role = "";

    private Customer(Builder builder)
    {
        this.name = builder.name;
        this.surname = builder.surname;
        this.dateOfBirth = builder.dateOfBirth;
        this.emailAddress = builder.emailAddress;
        this.role = builder.role;
    }

    public String getName()
    {
        return name;
    }


    public String getSurname()
    {
        return surname;
    }


    public String getDateOfBirth()
    {
        return dateOfBirth;
    }


    public String getEmailAddress()
    {
        return emailAddress;
    }


    public String getcustomerRole()
    {
        return role;
    }

    public String toString(){
        return "emailAddress: " + getEmailAddress() + "\nName: " + getName() + "\nSurname: " + getSurname() + "\nDate of birth: " + getDateOfBirth() + "\nRole: " + getcustomerRole();
    }

    public static class Builder
    {
        private String name, surname;
        private String dateOfBirth;
        private String emailAddress;
        private String role;

        public Builder(){}

        public Builder emailAddress(String value)
        {
            this.emailAddress = value;
            return this;
        }

        public Builder name(String value)
        {
            this.name = value;
            return this;
        }

        public Builder surname(String value)
        {
            this.surname = value;
            return this;
        }

        public Builder dateOfBirth(String value)
        {
            this.dateOfBirth = value;
            return this;
        }

        public Builder role(String role)
        {
            this.role = role;
            return this;
        }

        public Builder copy(Customer value)
        {
            this.name = value.getName();
            this.surname = value.getSurname();
            this.dateOfBirth = value.getDateOfBirth();
            this.emailAddress = value.getEmailAddress();
            this.role = value.getcustomerRole();

            return this;
        }

        public Customer build()
        {
            return new Customer(this);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return emailAddress != null ? emailAddress.equals(customer.emailAddress) : customer.emailAddress == null;

    }

    @Override
    public int hashCode()
    {
        return emailAddress != null ? emailAddress.hashCode() : 0;
    }

}

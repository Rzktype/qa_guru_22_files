package com.rzktype;

import java.util.List;
import java.util.Objects;

    public  class Employee {
        private String id;
        private Integer index;
        private Boolean isActive;
        private Integer age;
        private List<String> friends;
        private String greeting;
        private String favoriteFruit;


        public  String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean active) {
            isActive = active;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public List<String> getFriends() {
            return friends;
        }

        public void setFriends(List<String> friends) {
            this.friends = friends;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getFavoriteFruit() {
            return favoriteFruit;
        }

        public void setFavoriteFruit(String favoriteFruit) {
            this.favoriteFruit = favoriteFruit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return Objects.equals(id, employee.id) && Objects.equals(index, employee.index) && Objects.equals(isActive, employee.isActive) && Objects.equals(age, employee.age) && Objects.equals(friends, employee.friends) && Objects.equals(greeting, employee.greeting) && Objects.equals(favoriteFruit, employee.favoriteFruit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, index, isActive, age, friends, greeting, favoriteFruit);
        }
    }



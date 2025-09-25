package com.raksha_kavach.view;

public class CurrentUser {
    private static CurrentUser instance;
    
    private String docId;
    private String fullName;
    private String username;
    private String email;
    private String contact;
    private String aadhar;
    private String pan;
    private String password;
    private String disease;
    private String bloodGroup;
    private String hemoglobin;
    private String gender;
    private String dob;
    private String age;
    private String address;

    private CurrentUser() {}

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }
    private String profileImagePath;

public String getProfileImagePath() {
    return profileImagePath;
}

public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
}

    // Regular instance methods
    public void setDocId(String docId) { this.docId = docId; }
    public String getDocId() { return docId; }
    
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getFullName() { return fullName; }
    
    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }
    
    // Add all other getters and setters for remaining fields...
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    
    public void setContact(String contact) { this.contact = contact; }
    public String getContact() { return contact; }
    
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }
    public String getAadhar() { return aadhar; }
    
    public void setPan(String pan) { this.pan = pan; }
    public String getPan() { return pan; }
    
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    
    public void setDisease(String disease) { this.disease = disease; }
    public String getDisease() { return disease; }
    
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getBloodGroup() { return bloodGroup; }
    
    public void setHemoglobin(String hemoglobin) { this.hemoglobin = hemoglobin; }
    public String getHemoglobin() { return hemoglobin; }
    
    public void setGender(String gender) { this.gender = gender; }
    public String getGender() { return gender; }
    
    public void setDob(String dob) { this.dob = dob; }
    public String getDob() { return dob; }
    
    public void setAge(String age) { this.age = age; }
    public String getAge() { return age; }
    
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return address; }
}
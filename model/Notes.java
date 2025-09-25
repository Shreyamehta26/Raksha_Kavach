package com.raksha_kavach.model;

public class Notes {

    private String question;
    private String answer;
    private String userName;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Note [question=" + question + ", answer=" + answer + ",userName=" + userName + "]";

    }

}

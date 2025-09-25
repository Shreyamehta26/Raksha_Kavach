
package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SchemeDetailsPage {

        private final String schemeName;
        private final Stage stage;

        public SchemeDetailsPage(String schemeName, Stage stage) {
                this.schemeName = schemeName;
                this.stage = stage;
        }

        public VBox getLayout() {
                String description = getDescription(schemeName);
                String documents = getDocuments(schemeName);
                String benefits = getBenefits(schemeName);
                String officialWebsite = getOfficialWebsite(schemeName);

                // Create a TextFlow to hold formatted content
                TextFlow textFlow = new TextFlow();

                // Scheme name
                Text schemeTitle = new Text("Scheme: " + schemeName + "\n\n");
                schemeTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-fill: black;");

                // Description
                Text descTitle = new Text("Description:\n");
                descTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-fill: black;");
                Text descContent = new Text(description + "\n\n");
                descContent.setStyle("-fx-font-size: 14px; -fx-fill: black;");

                // Documents
                Text docsTitle = new Text("Documents Required:\n");
                docsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-fill: black;");
                Text docsContent = new Text(documents + "\n\n");
                docsContent.setStyle("-fx-font-size: 14px; -fx-fill: black;");

                // Benefits
                Text benefitsTitle = new Text("Benefits:\n");
                benefitsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-fill: black;");
                Text benefitsContent = new Text(benefits + "\n\n");
                benefitsContent.setStyle("-fx-font-size: 14px; -fx-fill: black;");

                // Official Website section
                Text websiteTitle = new Text("Official Website:\n");
                websiteTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-fill: black;");

                Text websiteLink = new Text(officialWebsite + "\n");
                websiteLink.setStyle("-fx-font-size: 14px; -fx-fill: #0066cc; -fx-underline: true;");
                websiteLink.setOnMouseClicked(e -> {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Open External Link");
                        confirm.setHeaderText("You are about to leave the application");
                        confirm.setContentText("This will open your browser. Continue?");

                        Optional<ButtonType> result = confirm.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                openWebsite(officialWebsite);
                        }
                });
                websiteLink.setOnMouseEntered(e -> {
                        websiteLink.setStyle("-fx-font-size: 14px; -fx-fill: #004499; -fx-underline: true;");
                        websiteLink.setCursor(javafx.scene.Cursor.HAND);
                });
                websiteLink.setOnMouseExited(e -> {
                        websiteLink.setStyle("-fx-font-size: 14px; -fx-fill: #0066cc; -fx-underline: true;");
                        websiteLink.setCursor(javafx.scene.Cursor.DEFAULT);
                });

                textFlow.getChildren().addAll(
                                schemeTitle,
                                descTitle, descContent,
                                docsTitle, docsContent,
                                benefitsTitle, benefitsContent,
                                websiteTitle, websiteLink);
                textFlow.setLineSpacing(5);
                textFlow.setPadding(new Insets(20));

                Button backButton = new Button("⬅ Back");
                backButton.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                                + "-fx-text-fill: white; -fx-font-size: 16px; "
                                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
                backButton.setOnAction(e -> goBack());

                ScrollPane scrollPane = new ScrollPane(textFlow);
                scrollPane.setFitToWidth(true);
                scrollPane.setStyle("-fx-background-color: white;");
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setPrefViewportHeight(600);
                scrollPane.setPrefWidth(600);

                VBox layout = new VBox(20, scrollPane, backButton);
                layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2C3E50, #4CA1AF);");
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(30));

                return layout;
        }

        // ... [Rest of the methods remain exactly the same as in your original code]
        // ...
        private void openWebsite(String url) {
                try {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                Desktop.getDesktop().browse(new URI(url));
                        } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Website Link");
                                alert.setHeaderText("Please visit the official website");
                                alert.setContentText(url);
                                alert.showAndWait();
                        }
                } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Could not open browser");
                        alert.setContentText("Please check your internet connection and try again.");
                        alert.showAndWait();
                }
        }

        private String getOfficialWebsite(String schemeName) {
                Map<String, String> websites = new HashMap<>();
                websites.put("Ayushman Bharat", "https://www.pmjay.gov.in/");
                websites.put(" Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                "https://www.jeevandayee.gov.in/MJPJAY/FrontServlet?requestType=CommonRH&actionVal=RightFrame&page=undefined%3E%3E%3Cb%3EMJPJAY%3C/b%3E&pageName=MJPJAY&mainMenu=About&subMenu=MJPJAY");
                websites.put("Pradhan Mantri Jan Arogya Yojana(PM-JAY)", "https://www.pmjay.gov.in/");
                websites.put("The National Health Mission (NHM)", "https://nhm.gov.in/");
                websites.put("Chief Minister's Relief Fund (CMRF)", "https://cmrf.maharashtra.gov.in/");
                websites.put("Rashtriya Arogya Nidhi(RAN)",
                                "https://mohfw.gov.in/major-programmes/poor-patients-financial-assistance/rashtriya-arogya-nidhi");

                return websites.getOrDefault(schemeName, "No official website available");
        }

        private void goBack() {
                HomePage homePage = new HomePage();
                homePage.setHomestage(stage);
                Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
                stage.setScene(homeScene);
        }

        private String getDescription(String schemeName) {
                Map<String, String> descriptions = new HashMap<>();
                descriptions.put("Ayushman Bharat", """
                                Ayushman Bharat PM-JAY is a national health insurance scheme launched in 2018.
                                It provides free healthcare services to economically vulnerable families across India.
                                Coverage: ₹5 lakh per family per year for secondary and tertiary care hospitalization.
                                Beneficiaries: Bottom 40% of the population as per Socio-Economic Caste Census 2011.
                                """);

                descriptions.put(" Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                """
                                                MJPJAY is a Maharashtra state health scheme offering cashless treatment for low-income families.
                                                It covers secondary and tertiary care in empaneled hospitals.
                                                Coverage: ₹1.5 lakh per family per year.
                                                Beneficiaries: Families with yellow or orange ration cards.
                                                """);

                descriptions.put("Pradhan Mantri Jan Arogya Yojana(PM-JAY)", """
                                PM-JAY is part of Ayushman Bharat, offering ₹5 lakh health coverage per family per year.
                                It targets the bottom 40% of India's population based on SECC 2011 data.
                                Covers 1,393 medical procedures including pre-existing conditions.
                                No restrictions on family size, age or gender.
                                """);

                descriptions.put("The National Health Mission (NHM)",
                                """
                                                NHM aims to provide accessible, affordable, and quality healthcare to rural and urban populations.
                                                It includes maternal, child health, and disease control programs.
                                                Focuses on reducing maternal and infant mortality rates.
                                                Strengthens primary healthcare infrastructure across India.
                                                """);

                descriptions.put("Chief Minister's Relief Fund (CMRF)",
                                """
                                                CMRF provides financial assistance for life-threatening medical conditions to economically weaker sections.
                                                It supports surgeries, transplants, and emergency care.
                                                Maximum assistance: ₹10 lakh for serious illnesses.
                                                Priority given to children, senior citizens, and women.
                                                """);

                descriptions.put("Rashtriya Arogya Nidhi(RAN)",
                                """
                                                RAN offers financial aid to BPL patients suffering from major illnesses requiring hospitalization.
                                                It is applicable in government hospitals only.
                                                Maximum assistance: ₹15 lakh for life-threatening diseases.
                                                Covers treatments like cancer, heart disease, and organ transplants.
                                                """);

                return descriptions.getOrDefault(schemeName, "No description available.");
        }

        private String getDocuments(String schemeName) {
                Map<String, String> documents = new HashMap<>();
                documents.put("Ayushman Bharat", """
                                - Aadhaar Card (or other government-issued ID)
                                - Ration Card / BPL Card
                                - Income Certificate (if applicable)
                                - Family Photograph
                                - Mobile Number (for registration)
                                - Bank Account Details (for cashless transactions)
                                """);

                documents.put(" Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)", """
                                - Yellow / Orange Ration Card
                                - Aadhaar Card of all family members
                                - Income Certificate (if not ration card holder)
                                - Residence Proof (Electricity bill/Voter ID)
                                - Recent passport-size photograph
                                """);

                documents.put("Pradhan Mantri Jan Arogya Yojana(PM-JAY)", """
                                - Aadhaar Card (for identity verification)
                                - PM-JAY ID or SECC-based eligibility proof
                                - Family Photograph
                                - Mobile Number (for OTP verification)
                                - Bank Account Details (for direct benefit transfer)
                                """);

                documents.put("The National Health Mission (NHM)", """
                                - Aadhaar Card (for identification)
                                - Health Card (if already enrolled)
                                - Referral Slip from PHC/CHC (for specialized care)
                                - BPL Card (for certain benefits)
                                - Maternity Card (for pregnant women)
                                """);

                documents.put("Chief Minister's Relief Fund (CMRF)", """
                                - Completed Application Form (available online)
                                - Income Certificate (proving financial need)
                                - Detailed Medical Reports and Diagnosis
                                - Hospital Estimate (for proposed treatment)
                                - Aadhaar Card and Address Proof
                                - Bank Account Details (for direct transfer)
                                """);

                documents.put("Rashtriya Arogya Nidhi(RAN)", """
                                - BPL Certificate (from competent authority)
                                - Aadhaar Card and Address Proof
                                - Medical Certificate (from government hospital)
                                - Detailed Treatment Plan and Cost Estimate
                                - Recommendation Letter from Hospital Superintendent
                                - Bank Account Details (for direct transfer)
                                """);

                return documents.getOrDefault(schemeName, "No document details available.");
        }

        private String getBenefits(String schemeName) {
                Map<String, String> benefits = new HashMap<>();
                benefits.put("Ayushman Bharat", """
                                - ₹5 lakh coverage per family per year
                                - Cashless treatment in 25,000+ empaneled hospitals
                                - Covers pre-existing conditions from day one
                                - No restrictions on family size, age or gender
                                - Covers 1,393 medical procedures
                                - Includes 3 days pre-hospitalization and 15 days post-hospitalization expenses
                                """);

                benefits.put(" Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)", """
                                - ₹1.5 lakh coverage per family per year
                                - Covers 996 medical and surgical procedures
                                - Cashless treatment in Maharashtra
                                - Available to all state residents with eligible ration cards
                                - Includes transportation costs for referred patients
                                - Covers follow-up treatments
                                """);

                benefits.put("Pradhan Mantri Jan Arogya Yojana(PM-JAY)", """
                                - ₹5 lakh coverage per family
                                - Covers 1,393 procedures including surgeries
                                - Paperless and cashless hospitalization
                                - Available across India with portability
                                - Covers pre-hospitalization and post-hospitalization expenses
                                - Includes diagnostics and medicines
                                """);

                benefits.put("The National Health Mission (NHM)", """
                                - Free maternal and child healthcare services
                                - Universal immunization program
                                - Disease control programs (TB, Malaria, etc.)
                                - Free essential medicines and diagnostics
                                - Strengthening of rural health infrastructure
                                - Training of healthcare workers
                                """);

                benefits.put("Chief Minister's Relief Fund (CMRF)", """
                                - Financial aid for critical surgeries and treatments
                                - Emergency support for accident victims
                                - Covers ICU charges, implants, and expensive medicines
                                - Special provisions for pediatric and geriatric cases
                                - Quick disbursement for urgent cases
                                - Transparent online application process
                                """);

                benefits.put("Rashtriya Arogya Nidhi(RAN)", """
                                - Financial support for major illnesses
                                - Applicable only in government hospitals
                                - Covers cancer, heart disease, kidney failure, etc.
                                - Maximum assistance of ₹15 lakh
                                - Special provisions for pediatric cases
                                - Can be combined with state health schemes
                                """);

                return benefits.getOrDefault(schemeName, "No benefit details available.");
        }
}
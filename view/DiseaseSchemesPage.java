// DiseaseSchemesPage.java
package com.raksha_kavach.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class DiseaseSchemesPage {

        private final String disease;
        private final Stage stage;

        public DiseaseSchemesPage(String disease, Stage stage) {
                this.disease = disease;
                this.stage = stage;
        }

        public VBox getLayout() {
                Label title = new Label("Schemes for: " + disease);
                title.setAlignment(Pos.CENTER);
                title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #002855;");
                title.setEffect(new javafx.scene.effect.DropShadow(5, Color.GRAY));

                Label paragraph = new Label(getSchemeParagraph(disease));
                paragraph.setWrapText(true);
                paragraph.setAlignment(Pos.CENTER);
                paragraph.setStyle(
                                "-fx-font-size: 15px; -fx-text-fill: #212529; -fx-background-color: #f1f1f1; -fx-margin:60;-fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #ccc; -fx-border-radius: 10;");

                VBox schemeButtons = new VBox(10);
                schemeButtons.setAlignment(Pos.CENTER);
                for (String scheme : getSchemesForDisease(disease)) {
                        Button schemeBtn = new Button(scheme);
                        schemeBtn.setStyle(
                                        "-fx-font-size:14px; -fx-padding:10 18; -fx-background-color: white; -fx-text-fill:black; -fx-background-radius:8;");
                        schemeBtn.setOnAction(e -> openSchemeDetails(scheme));
                        schemeButtons.getChildren().add(schemeBtn);
                }

                Button backBtn = new Button("\u2B05 Back");
                backBtn.setStyle("-fx-background-color: linear-gradient(to right, #5bc0de, #6f42c1); "
                                + "-fx-text-fill: white; -fx-font-size: 16px; "
                                + "-fx-background-radius: 10; -fx-padding: 8px 20px;");
                backBtn.setOnAction(e -> goBack());

                VBox card = new VBox(20, title, paragraph, new Label("\nAvailable Schemes:"), schemeButtons, backBtn);
                card.setAlignment(Pos.CENTER);
                card.setPadding(new Insets(30));
                card.setStyle(
                                "-fx-background-color: linear-gradient(to bottom right, #2C3E50, #4CA1AF); -fx-background-radius: 15; -fx-border-color: #90e0ef; -fx-border-width: 2; -fx-border-radius: 15;");
                card.setMaxWidth(1540);
                card.setMaxHeight(940);

                ScrollPane scrollPane = new ScrollPane(card);
                scrollPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #2C3E50, #4CA1AF);");
                scrollPane.setFitToWidth(true);
                scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                scrollPane.setPadding(new Insets(10));
                scrollPane.setPrefViewportHeight(840);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

                VBox layout = new VBox(scrollPane);
                layout.setPadding(new Insets(10));
                layout.setAlignment(Pos.CENTER);
                layout.setPrefSize(1540, 940);
                return layout;
        }

        private void openSchemeDetails(String schemeName) {
                SchemeDetailsPage detailsPage = new SchemeDetailsPage(schemeName, stage);
                Scene scene = new Scene(detailsPage.getLayout(), 1540, 940);
                stage.setScene(scene);
        }

        private void goBack() {
                HomePage homePage = new HomePage();
                homePage.setHomestage(stage);
                Scene homeScene = new Scene(homePage.createHomePage(null), 1540, 940);
                stage.setScene(homeScene);
        }

        private List<String> getSchemesForDisease(String disease) {
                Map<String, List<String>> schemeMap = new HashMap<>();
                schemeMap.put("Heart",
                                Arrays.asList("Ayushman Bharat", " Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                                "Pradhan Mantri Jan Arogya Yojana(PM-JAY)",
                                                "Chief Minister's Relief Fund (CMRF)",
                                                "The National Health Mission (NHM)"));
                schemeMap.put("Brain",
                                Arrays.asList("Ayushman Bharat", " Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                                "Pradhan Mantri Jan Arogya Yojana(PM-JAY)",
                                                "Chief Minister's Relief Fund (CMRF)",
                                                "Rashtriya Arogya Nidhi(RAN)"));
                schemeMap.put("Cancer",
                                Arrays.asList("Ayushman Bharat", " Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                                "Pradhan Mantri Jan Arogya Yojana(PM-JAY)",
                                                "Chief Minister's Relief Fund (CMRF)",
                                                "Rashtriya Arogya Nidhi(RAN)", "The National Health Mission (NHM)"));
                schemeMap.put("Eye", Arrays.asList(" Mahatma Jyotirao Phule Jan Arogya Yojana(MJPJAY)",
                                "Chief Minister's Relief Fund (CMRF)", "The National Health Mission (NHM)"));
                return schemeMap.getOrDefault(disease, Collections.singletonList("No schemes available."));
        }

        private String getSchemeParagraph(String disease) {
                Map<String, String> infoMap = new HashMap<>();
                infoMap.put("Heart",
                                "The Maharashtra government has launched multiple heart-related schemes such as MJPJAY, PM-JAY, and Ayushman Bharat to support economically disadvantaged patients. These schemes aim to cover surgeries, medication, hospital stay, and rehabilitation.\n\nEligibility: Individuals with BPL cards or income less than ₹1.5 lakh annually.\nDocuments Required: Aadhaar card, BPL certificate/income proof, medical referral.\nHospitals: JJ Hospital, Ruby Hall, Nair Hospital, KEM Hospital.\n\nPatients can get benefits of up to ₹5 lakh. Rural health camps conduct early diagnosis and provide transportation for treatment. Follow-ups, cardiac rehab, and medicine subsidies are part of the plan. Schemes also offer 24x7 helpline and CSC registration options.\n\nEmergency ambulance access and teleconsultation with cardiologists are provided in remote districts. Awareness drives on lifestyle changes and risk factors help prevent chronic cases.");
                infoMap.put("Eye",
                                "Eye-related schemes like Free Cataract Surgery and Eye Donation Yojana offer vision correction surgeries, post-op care, and awareness.\n\nEligibility: Any citizen with diagnosed visual disability or eye disorder.\nDocuments Required: Aadhaar, medical certification from an ophthalmologist.\nHospitals: Govt Eye Hospital, LVPEI, Sankara Nethralaya tie-ups.\n\nSchemes ensure rural screening via mobile vans, school eye checkups, and old-age vision recovery initiatives. Financial support includes surgery, transport, lenses, spectacles, and protective gear. Camps in tribal areas run quarterly.\n\nEye donation drives and cornea transplants are encouraged. NGOs collaborate to promote preventive measures and educate communities on eye hygiene.");
                infoMap.put("Cancer",
                                "The Cancer Yojana and MJPJAY Cancer Support are pivotal schemes for free or subsidized treatment. They encompass diagnosis, biopsy, chemotherapy, radiation, and palliative care.\n\nEligibility: Cancer patients from BPL families or referred by public hospitals.\nDocuments Required: Diagnosis certificate, Aadhaar, BPL card/income proof.\nHospitals: Tata Memorial, Sassoon, MGIMS, Solapur Cancer Institute.\n\nNutritional support and psychological counseling are integrated. Patients receive monthly follow-ups and travel cost reimbursements. Screening vans identify high-risk patients in tribal zones.\n\nSchemes also help with pain management medications and home visits post-discharge. Online portals provide real-time claim tracking and support networks.");
                infoMap.put("Brain",
                                "For brain-related critical care, NeuroCare Yojana and PM Neurosurgery Aid provide essential services for trauma, tumor, stroke, and epilepsy patients.\n\nEligibility: Emergency neuro cases from low-income backgrounds.\nDocuments Required: Aadhaar, specialist certificate, income proof.\nHospitals: AIIMS, KEM, Sion Hospital, GMC Nagpur.\n\nThe scheme funds ICU care, MRIs, surgeries, physiotherapy, and neuro medications. Critical airlifts are available from interior villages. Post-treatment rehabilitation is arranged via partner neuro rehab centers.\n\nNeuro health cards, telemedicine consultation, and social counseling support families. Educational sessions on brain injury prevention are also conducted at taluka level.");
                return infoMap.getOrDefault(disease, "No detailed information available for this disease.");
        }

        public Scene createScene() {
                throw new UnsupportedOperationException("Unimplemented method 'createScene'");
        }
}
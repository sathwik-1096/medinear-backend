package com.medinear.medinear.config;

import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.PharmacyInventory;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.PharmacyStatus;
import com.medinear.medinear.enums.Role;
import com.medinear.medinear.enums.UnitType;
import com.medinear.medinear.repository.MedicineRepository;
import com.medinear.medinear.repository.PharmacyInventoryRepository;
import com.medinear.medinear.repository.PharmacyRepository;
import com.medinear.medinear.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AdminDataInitializer {

    @Bean
    CommandLineRunner createInitialData(
            UserRepository userRepository,
            PharmacyRepository pharmacyRepository,
            MedicineRepository medicineRepository,
            PharmacyInventoryRepository inventoryRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // 1. Admin Users (Keep intact)
            if (!userRepository.existsByEmail("parachikapu.sathwik@gmail.com")) {
                User admin = new User();
                admin.setFirstName("Sathwik");
                admin.setLastName("Parachikapu");
                admin.setEmail("parachikapu.sathwik@gmail.com");
                admin.setPassword(passwordEncoder.encode("Sathwik@1096"));
                admin.setPhoneNumber("9999999999");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (!userRepository.existsByEmail("admin@demo.com")) {
                User admin = new User();
                admin.setFirstName("System");
                admin.setLastName("Admin");
                admin.setEmail("admin@demo.com");
                admin.setPassword(passwordEncoder.encode("demo123"));
                admin.setPhoneNumber("9876543210");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            // 2. Demo Primary Owner User
            User primaryOwner = userRepository.findByEmail("owner@demo.com").orElse(null);
            if (primaryOwner == null) {
                primaryOwner = new User();
                primaryOwner.setFirstName("Rajesh");
                primaryOwner.setLastName("Kumar");
                primaryOwner.setEmail("owner@demo.com");
                primaryOwner.setPassword(passwordEncoder.encode("demo123"));
                primaryOwner.setPhoneNumber("9876543211");
                primaryOwner.setRole(Role.OWNER);
                primaryOwner = userRepository.save(primaryOwner);
            }

            // 3. 20 Unique Owners
            String[][] ownerSeeds = {
                {"Rajesh", "Kumar", "owner@demo.com", "9876543211"},
                {"Priya", "Sharma", "priya.sharma@pharmacy.in", "9876543002"},
                {"Arjun", "Reddy", "arjun.reddy@pharmacy.in", "9876543003"},
                {"Sneha", "Kulkarni", "sneha.kulkarni@pharmacy.in", "9876543004"},
                {"Vikram", "Nair", "vikram.nair@pharmacy.in", "9876543005"},
                {"Anjali", "Desai", "anjali.desai@pharmacy.in", "9876543006"},
                {"Karthik", "Iyer", "karthik.iyer@pharmacy.in", "9876543007"},
                {"Meera", "Pillai", "meera.pillai@pharmacy.in", "9876543008"},
                {"Suresh", "Patel", "suresh.patel@pharmacy.in", "9876543009"},
                {"Anita", "Rao", "anita.rao@pharmacy.in", "9876543010"},
                {"Ramesh", "Verma", "ramesh.verma@pharmacy.in", "9876543011"},
                {"Kavita", "Joshi", "kavita.joshi@pharmacy.in", "9876543012"},
                {"Deepak", "Gupta", "deepak.gupta@pharmacy.in", "9876543013"},
                {"Sunita", "Agarwal", "sunita.agarwal@pharmacy.in", "9876543014"},
                {"Manoj", "Singh", "manoj.singh@pharmacy.in", "9876543015"},
                {"Lakshmi", "Narayana", "lakshmi.narayana@pharmacy.in", "9876543016"},
                {"Venkat", "Rao", "venkat.rao@pharmacy.in", "9876543017"},
                {"Swati", "Mishra", "swati.mishra@pharmacy.in", "9876543018"},
                {"Harish", "Chander", "harish.chander@pharmacy.in", "9876543019"},
                {"Divya", "Teja", "divya.teja@pharmacy.in", "9876543020"}
            };

            List<User> ownersList = new ArrayList<>();
            for (String[] oSeed : ownerSeeds) {
                User o = userRepository.findByEmail(oSeed[2]).orElse(null);
                if (o == null) {
                    o = new User();
                    o.setFirstName(oSeed[0]);
                    o.setLastName(oSeed[1]);
                    o.setEmail(oSeed[2]);
                    o.setPassword(passwordEncoder.encode("demo123"));
                    o.setPhoneNumber(oSeed[3]);
                    o.setRole(Role.OWNER);
                    o = userRepository.save(o);
                }
                ownersList.add(o);
            }

            // 4. 20 Unique Consumers
            String[][] consumerSeeds = {
                {"Anita", "Sharma", "consumer@demo.com", "9876543212"},
                {"Aarav", "Patel", "aarav.patel@gmail.com", "9876543102"},
                {"Divya", "Menon", "divya.menon@gmail.com", "9876543103"},
                {"Rohit", "Gupta", "rohit.gupta@gmail.com", "9876543104"},
                {"Ishita", "Bose", "ishita.bose@gmail.com", "9876543105"},
                {"Karan", "Singh", "karan.singh@gmail.com", "9876543106"},
                {"Nisha", "Rao", "nisha.rao@gmail.com", "9876543107"},
                {"Amit", "Shah", "amit.shah@gmail.com", "9876543108"},
                {"Pooja", "Hegde", "pooja.hegde@gmail.com", "9876543109"},
                {"Tarun", "Varma", "tarun.varma@gmail.com", "9876543110"},
                {"Snehal", "Shinde", "snehal.shinde@gmail.com", "9876543111"},
                {"Rahul", "Dravid", "rahul.dravid@gmail.com", "9876543112"},
                {"Neha", "Sharma", "neha.sharma@gmail.com", "9876543113"},
                {"Sanjay", "Kapoor", "sanjay.kapoor@gmail.com", "9876543114"},
                {"Ritu", "Saxena", "ritu.saxena@gmail.com", "9876543115"},
                {"Varun", "Dhawan", "varun.dhawan@gmail.com", "9876543116"},
                {"Shruti", "Hassan", "shruti.hassan@gmail.com", "9876543117"},
                {"Aditya", "Roy", "aditya.roy@gmail.com", "9876543118"},
                {"Bhavana", "Reddy", "bhavana.reddy@gmail.com", "9876543119"},
                {"Nikhil", "Kumar", "nikhil.kumar@gmail.com", "9876543120"}
            };

            for (String[] cSeed : consumerSeeds) {
                if (!userRepository.existsByEmail(cSeed[2])) {
                    User c = new User();
                    c.setFirstName(cSeed[0]);
                    c.setLastName(cSeed[1]);
                    c.setEmail(cSeed[2]);
                    c.setPassword(passwordEncoder.encode("demo123"));
                    c.setPhoneNumber(cSeed[3]);
                    c.setRole(Role.CONSUMER);
                    userRepository.save(c);
                }
            }

            // 5. 20 Unique Pharmacies
            if (pharmacyRepository.count() < 20) {
                String[][] pharmData = {
                    {"MediCare Central Pharmacy", "Road No 12, Banjara Hills", "500034", "17.4156", "78.4347", "040-23548901", "contact@medicarecentral.com", "HYD-PH-2026-001"},
                    {"CarePlus Pharmacy & Wellness", "Hitech City Main Road, Madhapur", "500081", "17.4483", "78.3915", "040-49583020", "info@carepluspharma.com", "HYD-PH-2026-002"},
                    {"City Health Chemist", "Kukatpally Housing Board Colony", "500072", "17.4849", "78.4011", "040-39482710", "support@cityhealth.com", "HYD-PH-2026-003"},
                    {"Apollo Pharmacy Express", "Financial District Rd, Gachibowli", "500032", "17.4401", "78.3489", "040-29384750", "gachibowli@apollomeds.com", "HYD-PH-2026-004"},
                    {"MedPlus Health Services", "Ameerpet Metro Station Rd", "500016", "17.4375", "78.4483", "040-38475620", "ameerpet@medplus.com", "HYD-PH-2026-005"},
                    {"WellCare Medicals", "MG Road, Secunderabad", "500003", "17.4399", "78.4983", "040-58473620", "secunderabad@wellcare.com", "HYD-PH-2026-006"},
                    {"LifeLine 24/7 Pharmacy", "Road No 36, Jubilee Hills", "500033", "17.4312", "78.4072", "040-67584930", "help@lifeline247.com", "HYD-PH-2026-007"},
                    {"CurePoint Chemist", "Cyber Gateway Rd, Hitec City", "500081", "17.4516", "78.3808", "040-76859403", "hiteccity@curepoint.com", "HYD-PH-2026-008"},
                    {"GreenCross Pharmacy", "King Koti Rd, Abids", "500001", "17.3951", "78.4790", "040-85940321", "abids@greencross.com", "HYD-PH-2026-009"},
                    {"HealthFirst Druggist", "Rashtrapati Road, Begumpet", "500016", "17.4440", "78.4670", "040-94032185", "begumpet@healthfirst.com", "HYD-PH-2026-010"},
                    {"Sanjeevani Pharmacy", "Main Road, Kondapur", "500084", "17.4622", "78.3568", "040-12345678", "kondapur@sanjeevani.com", "HYD-PH-2026-011"},
                    {"Royal Meds Store", "Nagarjuna Circle, Panjagutta", "500082", "17.4256", "78.4512", "040-23456789", "panjagutta@royalmeds.com", "HYD-PH-2026-012"},
                    {"Apex Healthcare Pharmacy", "X Roads, Miyapur", "500049", "17.4968", "78.3614", "040-34567890", "miyapur@apexhealth.com", "HYD-PH-2026-013"},
                    {"Swastha Pharmacy", "Liberty Road, Himayatnagar", "500029", "17.4025", "78.4842", "040-45678901", "himayatnagar@swastha.com", "HYD-PH-2026-014"},
                    {"Pulse Medicos", "Secretariat Colony, Manikonda", "500089", "17.4012", "78.3789", "040-56789012", "manikonda@pulsemedicos.com", "HYD-PH-2026-015"},
                    {"Global Life Chemist", "Police Station Rd, Lakdikapul", "500004", "17.4045", "78.4632", "040-67890123", "lakdikapul@globallife.com", "HYD-PH-2026-016"},
                    {"Sunshine Pharmacy", "Raj Bhavan Rd, Somajiguda", "500082", "17.4278", "78.4589", "040-78901234", "somajiguda@sunshinepharma.com", "HYD-PH-2026-017"},
                    {"VitalCare Meds", "Main Rd, Dilsukhnagar", "500060", "17.3688", "78.5247", "040-89012345", "dilsukhnagar@vitalcare.com", "HYD-PH-2026-018"},
                    {"Metro Pharmacy", "Osmania Hospital Rd, Koti", "500095", "17.3824", "78.4811", "040-90123456", "koti@metropharma.com", "HYD-PH-2026-019"},
                    {"Med Express Chemist", "Seven Tombs Rd, Toli Chowki", "500008", "17.3989", "78.4125", "040-01234567", "tolichowki@medexpress.com", "HYD-PH-2026-020"}
                };

                List<Pharmacy> savedPharmacies = new ArrayList<>();
                for (int i = 0; i < pharmData.length; i++) {
                    String[] p = pharmData[i];
                    if (!pharmacyRepository.existsByLicenseNumber(p[7])) {
                        Pharmacy pharm = new Pharmacy();
                        pharm.setPharmacyName(p[0]);
                        pharm.setAddress(p[1]);
                        pharm.setCity("Hyderabad");
                        pharm.setState("Telangana");
                        pharm.setPincode(p[2]);
                        pharm.setLatitude(Double.parseDouble(p[3]));
                        pharm.setLongitude(Double.parseDouble(p[4]));
                        pharm.setPhoneNumber(p[5]);
                        pharm.setEmail(p[6]);
                        pharm.setLicenseNumber(p[7]);
                        pharm.setOpeningTime(LocalTime.of(8, 0));
                        pharm.setClosingTime(LocalTime.of(22, 30));
                        pharm.setStatus(PharmacyStatus.APPROVED);
                        pharm.setOwner(ownersList.get(i % ownersList.size()));
                        savedPharmacies.add(pharmacyRepository.save(pharm));
                    }
                }

                // 6. 20 Unique Medicines
                String[][] medSeeds = {
                    {"Paracetamol 650mg", "Micro Labs", "650mg", "Analgesic"},
                    {"Amoxicillin 500mg", "Cipla Ltd", "500mg", "Antibiotics"},
                    {"Ibuprofen 400mg", "Sun Pharma", "400mg", "Analgesic"},
                    {"Cetirizine 10mg", "Dr. Reddy's", "10mg", "Antihistamine"},
                    {"Metformin 500mg", "Lupin", "500mg", "Antidiabetic"},
                    {"Atorvastatin 10mg", "Zydus Cadila", "10mg", "Cardiovascular"},
                    {"Omeprazole 20mg", "Torrent Pharma", "20mg", "Gastrointestinal"},
                    {"Azithromycin 500mg", "Alembic", "500mg", "Antibiotics"},
                    {"Pantoprazole 40mg", "Alkem Labs", "40mg", "Gastrointestinal"},
                    {"Vitamin C 500mg", "Limcee Health", "500mg", "Vitamins"},
                    {"ORS Orange Sachet", "Electral", "21.8g", "Rehydration"},
                    {"Digene Antacid Gel", "Abbott India", "200ml", "Antacid"},
                    {"Benadryl Cough Syrup", "Kenvue", "100ml", "Cough & Cold"},
                    {"Telmisartan 40mg", "Glenmark", "40mg", "Cardiovascular"},
                    {"Amlodipine 5mg", "Ipca Labs", "5mg", "Cardiovascular"},
                    {"Dolo 650 Tablet", "Micro Labs", "650mg", "Analgesic"},
                    {"Montelukast 10mg", "Mankind Pharma", "10mg", "Respiratory"},
                    {"Ranitidine 150mg", "JB Pharma", "150mg", "Gastrointestinal"},
                    {"Multivitamin Revital", "Sun Pharma", "1 capsule", "Vitamins"},
                    {"Levofloxacin 500mg", "Hetero Healthcare", "500mg", "Antibiotics"}
                };

                List<Medicine> savedMedicines = new ArrayList<>();
                for (String[] m : medSeeds) {
                    savedMedicines.add(createMedicine(medicineRepository, m[0], m[1], m[2], m[3]));
                }

                // 7. Seed Inventory across pharmacies and medicines
                if (!savedPharmacies.isEmpty() && !savedMedicines.isEmpty()) {
                    int bCount = 100;
                    for (Pharmacy ph : savedPharmacies) {
                        for (int mIdx = 0; mIdx < savedMedicines.size(); mIdx++) {
                            if ((ph.getId() + mIdx) % 2 == 0) {
                                Medicine med = savedMedicines.get(mIdx);
                                double basePrice = 25.0 + (mIdx * 12.5);
                                seedInv(inventoryRepository, ph, med, 80 + (mIdx * 5), basePrice, UnitType.STRIP, "BATCH-" + (bCount++), LocalDate.now().plusMonths(12 + mIdx), 15);
                            }
                        }
                    }
                }
            }
        };
    }

    private Medicine createMedicine(MedicineRepository repo, String name, String manufacturer, String strength, String category) {
        List<Medicine> existing = repo.findByMedicineNameIgnoreCase(name);
        if (!existing.isEmpty()) return existing.get(0);
        Medicine m = new Medicine();
        m.setMedicineName(name);
        m.setManufacturer(manufacturer);
        m.setStrength(strength);
        m.setCategory(category);
        return repo.save(m);
    }

    private void seedInv(PharmacyInventoryRepository repo, Pharmacy pharmacy, Medicine medicine, int qty, double price, UnitType unit, String batch, LocalDate expiry, int minStock) {
        PharmacyInventory inv = new PharmacyInventory();
        inv.setPharmacy(pharmacy);
        inv.setMedicine(medicine);
        inv.setAvailableQuantity(qty);
        inv.setPrice(price);
        inv.setUnit(unit);
        inv.setBatchNumber(batch);
        inv.setExpiryDate(expiry);
        inv.setMinimumStock(minStock);
        inv.setManufacturer(medicine.getManufacturer());
        repo.save(inv);
    }
}
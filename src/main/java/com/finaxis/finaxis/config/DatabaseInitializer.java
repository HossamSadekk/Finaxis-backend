package com.finaxis.finaxis.config;

import com.finaxis.finaxis.entity.Bank;
import com.finaxis.finaxis.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DatabaseInitializer {

    BankRepository bankRepository;

    @Autowired
    public DatabaseInitializer(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @PostConstruct
    public void init() {
        if (bankRepository.count() == 0) { // Check if there are no banks already in the database
            List<Bank> banks = Arrays.asList(
                    Bank.builder().name("National Bank of Egypt (NBE)").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fbank_alahly.jpg?alt=media&token=ed80df47-5a44-4409-86d5-f2b7193f52ac").IBAN("NBE_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Banque Misr").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fbank_misr.jpg?alt=media&token=e20f32d0-5e1a-42fe-abca-541765ea5243").IBAN("BM_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Banque du Caire").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fbank_cairo.jpg?alt=media&token=c5b85ec8-8cf7-444a-afa2-a87ee15b596e").IBAN("BDC_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Commercial International Bank (CIB)").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fcib.jpg?alt=media&token=056cabeb-9fab-4a53-961e-71258303f9c3").IBAN("CIB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Alexandria Bank").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Falex_bank.jpg?alt=media&token=eb1c6986-931f-44b9-9da7-b60f6b0b104a").IBAN("AB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Arab African International Bank").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Farab_african_bank.jpg?alt=media&token=9b06e601-b8a4-479a-a641-dd304d9159ca").IBAN("AAIB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Agricultural Bank of Egypt").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fagricultural_bank.jpg?alt=media&token=5c74a79c-910b-45ff-a2f5-8e59448c5c73").IBAN("ABE_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Egyptian Gulf Bank").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Feg_bank.jpg?alt=media&token=8ac47a0f-d1cd-4298-a556-cd12adf204f6").IBAN("EGB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("QNB Alahli").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fqnb_bank.jpg?alt=media&token=c192258e-3f0a-4e34-97d1-33357e6eac0d").IBAN("QNB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("HSBC Bank Egypt").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fhsbc_bank.jpg?alt=media&token=ce510410-cb8f-42df-942a-fd450f805e77").IBAN("HSBC_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Mashreq Bank").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fmashreq_bank.jpg?alt=media&token=17f3d0a3-92f9-4891-960f-bd7860d45799").IBAN("Mashreq_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Faisal Islamic Bank of Egypt").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Ffaisal_bank.jpg?alt=media&token=0d26f4f3-7105-48a8-add6-5c7d0df30316").IBAN("FIBE_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Al Baraka Bank Egypt").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Falbaraka_bank.jpg?alt=media&token=76327815-d795-4163-b22d-7ad907f48d1e").IBAN("ABB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                    Bank.builder().name("Suez Canal Bank").avatar("https://firebasestorage.googleapis.com/v0/b/finaxis-backend.appspot.com/o/banks%2Fsuez_bank.jpg?alt=media&token=b7b21d7e-bdbe-4c08-88fa-f3cb94b426dd").IBAN("EDB_IBAN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build()
            );

            bankRepository.saveAll(banks);
        }
    }
}


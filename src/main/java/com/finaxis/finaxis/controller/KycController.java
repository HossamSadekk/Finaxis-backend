package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.BankAccount;
import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.model.ErrorResponseModel;
import com.finaxis.finaxis.model.GenericResponseModel;
import com.finaxis.finaxis.model.kyc.BankSelectionRequest;
import com.finaxis.finaxis.services.AccountService;
import com.finaxis.finaxis.services.BankAccountService;
import com.finaxis.finaxis.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @PostMapping("/select-bank")
    public ResponseEntity<GenericResponseModel> selectBank(@RequestBody BankSelectionRequest request) {
        // Store the selected bank ID in the session
        httpSession.setAttribute("selectedBankId", request.getBankId());
        return ResponseEntity.ok(new GenericResponseModel(HttpStatus.OK, "Bank Selected"));
    }

    @PostMapping("/phone-number")
    public ResponseEntity<?> postPhoneNumber(@RequestParam String phoneNumber) {
        try {
            // Retrieve the selected bank ID from the session
            Long selectedBankId = (Long) httpSession.getAttribute("selectedBankId");

            if (selectedBankId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseModel(HttpStatus.BAD_REQUEST, "Session Expired - No bank selected", false));
            }

            // Use the BankAccountService to check if the phone number exists
            boolean phoneNumberExists = bankAccountService.isPhoneNumberAssociatedWithBank(selectedBankId, phoneNumber);

            if (phoneNumberExists) {
                // Save the phone number in the session
                httpSession.setAttribute("selectedPhoneNumber", phoneNumber);
                return ResponseEntity.ok(new GenericResponseModel(HttpStatus.OK, "Phone number matches and is saved in session"));
            } else {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ErrorResponseModel(NOT_FOUND, "Phone number is not associated with selected bank", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseModel(INTERNAL_SERVER_ERROR, e.getMessage(), false));
        }
    }

    @PostMapping("/submit-username")
    public ResponseEntity<?> submitUsername(@RequestParam String username) {
        try {
            if (accountService.isUsernameTaken(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponseModel(HttpStatus.CONFLICT, "Username is already taken", false));
            } else {
                httpSession.setAttribute("selectedUsername", username);
                return ResponseEntity.ok(new GenericResponseModel(HttpStatus.OK, "Username is available and saved in session"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseModel(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false));
        }
    }

    @PostMapping("/submit-card")
    public ResponseEntity<?> submitCardDetails(@RequestParam String cardNumber,
                                               @RequestParam String cardPassword, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long selectedBankId = (Long) httpSession.getAttribute("selectedBankId");
            String selectedPhoneNumber = (String) httpSession.getAttribute("selectedPhoneNumber");
            String selectedUsername = (String) httpSession.getAttribute("selectedUsername");

            if (selectedBankId == null || selectedPhoneNumber == null || selectedUsername == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseModel(HttpStatus.BAD_REQUEST, "Session Expired , Missing bank or phone number", false));
            }

            boolean isCardValid = bankAccountService.isCardNumberAndPasswordValid(selectedBankId, selectedPhoneNumber, cardNumber, cardPassword);

            if (isCardValid) {
                BankAccount bankAccount = bankAccountService.getBankAccount(selectedPhoneNumber);
                String username = userDetails.getUsername();
                User user = userService.findUserByUsername(username);
                Account newAccount = userService.createAccountForUser(user, bankAccount,selectedUsername);

                return ResponseEntity.ok(newAccount);

            } else {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ErrorResponseModel(NOT_FOUND, "Card details are incorrect", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseModel(INTERNAL_SERVER_ERROR, e.getMessage(), false));
        }
    }

}

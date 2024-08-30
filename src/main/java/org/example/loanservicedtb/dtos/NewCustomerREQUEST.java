package org.example.loanservicedtb.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCustomerREQUEST {

    public String reference;
    public String productId;
    public String customerType;
    public Kyc kyc;
    public LoanRequest loanRequest;
    public boolean termsAndConditionsAccepted;
    public boolean privacyPolicyAccepted;
    public String callBackUrl;

    public static class IdentificationDocuments{
        public String dob;
        public String passportPhoto;
        public String kraPin;
        public String idDocumentNumber;
        public String idDocumentFront;
        public String idDocumentBack;
        public String idDocumentIssueDate;
        public String idDocumentExpiryDate;
    }

    @Getter
    @Setter
    public static class Kyc{
        public String firstName;
        public String middleName;
        public String lastName;
        public String mobileNumber;
        public String email;
        public String nationality;
        public PhysicalAddress physicalAddress;
        public String pepPipDeclarationStatus;
        public String pepPipRemarks;
        public IdentificationDocuments identificationDocuments;
        public UsCitizenKYC usCitizenKYC;
    }
    @Getter
    @Setter
    public static class LoanRequest{
        public int loanAmount;
        public int loanTenure;
        public String loanPurpose;
        public String providerItemReference;
        public Object supportingDocument;
        public boolean providerApprovalProvided;
        public Object providerApprovalDocument;
    }

    @Getter
    @Setter
    public static class PhysicalAddress{
        public String street;
        public String city;
        public String country;
    }

    @Getter
    @Setter
    public static class UsCitizenKYC{
        public boolean isUsCitizen;
        public String ssn;
        public Object w9Form;
    }
}

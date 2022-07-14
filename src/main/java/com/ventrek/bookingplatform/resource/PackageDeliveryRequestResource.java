package com.ventrek.bookingplatform.resource;

import com.ventrek.bookingplatform.domain.*;
import com.ventrek.bookingplatform.service.FileService;
import com.ventrek.bookingplatform.service.PackageService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1")
public class PackageDeliveryRequestResource {

    @Autowired
    private PackageService packageService;


    @Autowired
    private FileService fileService;

    @GetMapping("packages")
    public ResponseEntity<List<PackageDeliveryRequest>> getPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getPackageDeliveryRequests(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("packages/delivery-requests")
    public ResponseEntity<List<PackageDeliveryRequest>> getDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getDeliveryRequests(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/ongoing")
    public ResponseEntity<List<PackageDeliveryRequest>> getOngoingDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                   @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getOngoingDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/upcoming")
    public ResponseEntity<List<PackageDeliveryRequest>> getUpcomingDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                          @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getUpcomingDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/completed")
    public ResponseEntity<List<PackageDeliveryRequest>> getCompletedDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                           @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getCompletedDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/rejected")
    public ResponseEntity<List<PackageDeliveryRequest>> getRejectedDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                           @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getRejectedDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/not-allocated")
    public ResponseEntity<List<PackageDeliveryRequest>> getNotAllocatedDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                           @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getNotAllocatedDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("deliveries/expired")
    public ResponseEntity<List<PackageDeliveryRequest>> getExpiredDeliveryRequestPackages(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                               @RequestParam(defaultValue = "id") String sortBy) {
        List<PackageDeliveryRequest> packageDeliveryRequests = this.packageService.getExpiredDeliveries(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(packageDeliveryRequests);
    }

    @GetMapping("packages/{id}")
    public ResponseEntity<PackageDeliveryRequest> getPackage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.packageService.getPackageDeliveryRequestById(id));
    }

    @GetMapping("paymentproof/packages/hash/{hash}")
    public ResponseEntity<PackageDeliveryRequest> getPackageWithHash(@PathVariable String hash) {
        byte[] decode = Base64.getDecoder().decode(hash);
        String h = new String(decode);

        long id =Long.parseLong(h);
        PackageDeliveryRequest packageDeliveryRequest = this.packageService.getPackageDeliveryRequestById(id);
        if(packageDeliveryRequest.isPaymentProofLinkUsed()){
            return ResponseEntity.status(HttpStatus.GONE).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(this.packageService.getPackageDeliveryRequestById(id));
        }
    }

    @PatchMapping("packages/{id}")
    public ResponseEntity<PackageDeliveryRequest> updatePackageStatus(@PathVariable Long id, @RequestBody PackageDeliveryRequest packageDeliveryRequest) {
        PackageDeliveryRequest updatedPackageDeliveryRequest = this.packageService.updatePackageStatus(id, packageDeliveryRequest.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPackageDeliveryRequest);
    }

    @PatchMapping("packages/{id}/payment")
    public ResponseEntity<PackageDeliveryRequest> updatePaidAmount(@PathVariable Long id, @RequestBody PackageDeliveryRequest packageDeliveryRequest) {
        PackageDeliveryRequest updatedPackageDeliveryRequest = this.packageService.updatePackagePaidAmount(id, packageDeliveryRequest.getPaidAmount());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPackageDeliveryRequest);
    }


    @PostMapping("packages")
    public ResponseEntity<PackageDeliveryRequest> createPackageRequest(@RequestBody PackageDeliveryRequest packageDeliveryRequest) throws MessagingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(packageDeliveryRequest.getStatus()==null){
            packageDeliveryRequest.setStatus(PackageDeliveryRequestStatus.PAYMENT_PENDING);
        }
        PackageDeliveryRequest createdPackageDeliveryRequest = packageService.createPackageDeliveryRequest(packageDeliveryRequest);
        byte[] bytesOfMessage = createdPackageDeliveryRequest.getId().toString().getBytes("UTF-8");
        String encodedHash  = Long.toHexString(createdPackageDeliveryRequest.getId());
        String encodedString = Base64.getEncoder().encodeToString(createdPackageDeliveryRequest.getId().toString().getBytes());

//        String encodedHash = Base64.getEncoder().encodeToString(bytesOfMessage);


        String paymentSlipUploadLink = "http://localhost:3000/payments/"+encodedString;
        String mail = "<html>Hello "+packageDeliveryRequest.getCustomer().getCustomerName()+", <br>\n" +
                      "Thank you for choosing us for delivering your package.\n" +
                       "<table id=\"vertical-1\" border=\"2px\">\n" +
                "        <caption>Delivery Request Summery</caption>\n" +
                "        <tr>\n" +
                "            <th>Pickup Address</th>\n" +
                "            <td>"+packageDeliveryRequest.getPickupAddress()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Drop-off Address</th>\n" +
                "            <td>"+packageDeliveryRequest.getDropOffAddress()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Distance(Km)</th>\n" +
                "            <td>"+packageDeliveryRequest.getDistance()/1000+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Description</th>\n" +
                "            <td>"+packageDeliveryRequest.getPackageDescription()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Special Remarks</th>\n" +
                "            <td>"+packageDeliveryRequest.getSpecialRemarks()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>\n" +
                "                Approximate weight</th>\n" +
                "            <td>"+packageDeliveryRequest.getWeight()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Pickup Date</th>\n" +
                "            <td>"+packageDeliveryRequest.getPickupDate()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Pickup Time</th>\n" +
                "            <td>"+packageDeliveryRequest.getPickupTime()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>Delivery Fee (AUD)</th>\n" +
                "            <td>"+packageDeliveryRequest.getDeliveryFee()+"</td>\n" +
                "        </tr>\n" +
                "    </table>"+
                      "Please pay the amount and upload the payment slip to the following link.\n " +
                      "Payment slip upload link: <a href="+paymentSlipUploadLink+">Upload Here</a>\n" +
                      "Thank you, Best regards.\n"+
                      "<b>GradeAMovers</b></html>";

        Mail htmlMail = new Mail();
        htmlMail.setFrom("kanishka.aws.02@gmail.com");
        htmlMail.setTo(packageDeliveryRequest.getCustomer().getCustomerEmail());
        htmlMail.setSubject("Your Package Delivery Request Placed");
        htmlMail.setBody(mail);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackageDeliveryRequest);
    }

    @PostMapping(
            path = "packages/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> uploadPackageImages(Long packageDeliveryRequestId,
                                                          @RequestParam("files") MultipartFile[] files) {

        if (files.length==0) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        for(MultipartFile file: files) {
            //Check if the file is an image
            if (!Arrays.asList(ContentType.IMAGE_PNG.getMimeType(),
                    ContentType.IMAGE_BMP.getMimeType(),
                    ContentType.IMAGE_GIF.getMimeType(),
                    ContentType.IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("FIle uploaded is not an image");
            }
            //get file metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));
            //Save Image in S3 and then save in the database
            String path = String.format("%s/%s", BucketName.PACKAGE_IMAGES.getBucketName(), UUID.randomUUID());
            String fileName = String.format("%s", file.getOriginalFilename());
            try {
                fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }

            PackageDeliveryRequest packageDeliveryRequest = packageService.getPackageDeliveryRequestById(packageDeliveryRequestId);

            PackageImage packageImage = PackageImage.builder()
                    .imageFileName(fileName)
                    .imagePath(path)
                    .packageDeliveryRequest(packageDeliveryRequest)
                    .build();

            packageService.createPackageImageUpload(packageImage);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(
            path = "payments/proof",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> uploadPaymentProof(Long packageDeliveryRequestId, String description, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_BMP.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType(),
                ContentType.IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save in the database
        String path = String.format("%s/%s", BucketName.PACKAGE_PAYMENT_PROOFS.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        PackageDeliveryRequest packageDeliveryRequest = packageService.getPackageDeliveryRequestById(packageDeliveryRequestId);

        PaymentProof paymentProof = PaymentProof.builder()
                .imageFileName(fileName)
                .imagePath(path)
                .note(description)
                .packageDeliveryRequest(packageDeliveryRequest)
                .build();
        packageService.createPaymentProof(paymentProof);

        packageDeliveryRequest.setPaymentProof(paymentProof);
        packageDeliveryRequest.setPaymentProofLinkUsed(true);
        packageDeliveryRequest.setStatus(PackageDeliveryRequestStatus.ADMIN_APPROVAL_PENDING);
        packageService.updatePackageDeliveryRequest(packageDeliveryRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/payments/proof/{id}/download")
    public String downloadTodoImage(@PathVariable("id") Long id) {
        PackageDeliveryRequest packageDeliveryRequest = packageService.getPackageDeliveryRequestById(id);
        PaymentProof paymentProof = packageService.getPaymentProof(packageDeliveryRequest.getPaymentProof().getId());
        byte[] imageBytes = fileService.download(paymentProof.getImagePath(), paymentProof.getImageFileName());
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @GetMapping("/distance")
    public ResponseEntity<DistanceMatrix> getDistance(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=driving&units=metric&language=en&avoid=&key=AIzaSyDpL0YWqm79YWD9b0SwEdrrWtrHFxNjXg8";
        ResponseEntity<DistanceMatrix> response
                = restTemplate.getForEntity(fooResourceUrl, DistanceMatrix.class);
        return response;
    }

}

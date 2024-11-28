package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.StudentBO;
import com.zenveus.the_culinary_academy.controllers.LoginController;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentProgramDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentProgramTransDAO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.dto.StudentDto;
import com.zenveus.the_culinary_academy.dto.StudentProgramDto;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;
import com.zenveus.the_culinary_academy.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentBOIMPL implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    StudentProgramDAO studentProgramDAO = (StudentProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENTPROGRAM);
    StudentProgramTransDAO studentProgramTransDAO = (StudentProgramTransDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENTPROGRAMTRANS);

    @Override
    public void saveStudent(StudentDto studentDTO) {
        // Implementation here
    }

    @Override
    public void deleteStudent(String id) {
        // Implementation here
    }

    @Override
    public void updateStudent(StudentDto studentDTO) {
        // Implementation here
    }

    @Override
    public StudentDto searchStudentByStudentId(String id) {
        // Implementation here
        Student student = null;
        try {
            student = studentDAO.exist(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new StudentDto(student.getStudentId(), student.getStudentNic(), student.getDob(), student.getFullName(), student.getAddress(), student.getEmail(), student.getPhone(), student.getRegistrationDate(), student.getRegistrationTime());
    }

    @Override
    public List<StudentDto> getAllStudents() {
        ArrayList<StudentDto> studentDtoList = new ArrayList<>();
        List<Student> allStudents = null;
        try {
            allStudents = studentDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Student student : allStudents) {
            studentDtoList.add(new StudentDto(student.getStudentId(), student.getStudentNic(), student.getDob(), student.getFullName(), student.getAddress(), student.getEmail(), student.getPhone(), student.getRegistrationDate(), student.getRegistrationTime()));
        }

        return studentDtoList;
    }

    @Override
    public boolean saveStudentAndPrograms(StudentDto studentDto, String[][] programDetailsArray, String[][] paymentDetailsArray) {
        Student student = new Student(studentDto.getStudentId(), studentDto.getStudentNic(), studentDto.getDob(), studentDto.getFullName(), studentDto.getAddress(), studentDto.getEmail(), studentDto.getPhone(), studentDto.getRegistrationDate(), studentDto.getRegistrationTime());

        String[][] combinedDetailsArray = combineProgramAndPaymentDetails(programDetailsArray, paymentDetailsArray);

        double totalAmount = 0;
        for (int i = 0; i < combinedDetailsArray.length; i++) {
            for (int j = 0; j < combinedDetailsArray[i].length; j++) {
                if (j == 2) {
                    totalAmount += Double.parseDouble(combinedDetailsArray[i][j]);
                }
                System.out.print(combinedDetailsArray[i][j] + " ");
            }
            System.out.println();
        }

        LoginController loginController = new LoginController();
        UserDto userDto = loginController.getLoginUser();

        List<StudentProgramDto> studentProgramDto = setStudentProgramDetails(studentDto, combinedDetailsArray);

        List<StudentProgram> studentProgram = new ArrayList<>();
        for (StudentProgramDto dto : studentProgramDto) {
            StudentProgram studentProgram1 = new StudentProgram();
            studentProgram1.setStudent(student);
            studentProgram1.setProgram(new Program(dto.getProgram().getProgramId(), dto.getProgram().getProgramName(), dto.getProgram().getDuration(), dto.getProgram().getFee()));
            studentProgram1.setPayOption(dto.getPayOption());
            studentProgram1.setInstallmentFee(dto.getInstallmentFee());
            studentProgram1.setTotalDue(dto.getTotalDue());
            studentProgram1.setPayStatus(dto.getPayStatus());
            studentProgram.add(studentProgram1);
        }



        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setPaymentDescription("Advance Payment");
        payment.setPaymentDate(student.getRegistrationDate());
        payment.setPaymentTime(student.getRegistrationTime());
        payment.setAmount(totalAmount);
        payment.setUser(new User(
                userDto.getUserId(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getAddress(),
                userDto.getJobRole(),
                userDto.getUsername(),
                userDto.getPassword()
        ));

        boolean isAdded = false;
        try {
            isAdded = studentProgramTransDAO.addStudentAndPrograms(student, studentProgram, payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return isAdded;
    }

    @Override
    public List<String[]> getProgramsForStudent(String studentId) {
        // List to store program details as String arrays
        List<String[]> programDetailsList = new ArrayList<>();

        try {
            // Retrieve StudentProgram entities for the given studentId
            List<StudentProgram> studentPrograms = studentProgramDAO.getProgramsForStudent(studentId);

            for (StudentProgram studentProgram : studentPrograms) {
                // Fetch the corresponding program details
                Program program = programDAO.exist(studentProgram.getProgram().getProgramId());

                // Add program details as a String array to the list
                programDetailsList.add(new String[]{
                        program.getProgramId(),
                        program.getProgramName(),
                        program.getDuration(),
                        String.valueOf(program.getFee()),
                        studentProgram.getPayOption(),
                        String.valueOf(studentProgram.getInstallmentFee()),
                        String.valueOf(studentProgram.getTotalDue()),
                        studentProgram.getPayStatus()
                });
            }
        } catch (Exception e) {
            // Log the exception and throw a runtime exception with a meaningful message
            e.printStackTrace();
            throw new RuntimeException("Error fetching programs for student ID: " + studentId, e);
        }

        return programDetailsList;
    }

    private List<StudentProgramDto> setStudentProgramDetails(StudentDto student, String[][] combinedDetailsArray) {
        List<StudentProgramDto> studentProgramDtoList = new ArrayList<>();
        for (int i = 0; i < combinedDetailsArray.length; i++) {
            String programId = combinedDetailsArray[i][0];
            String paymentOption = combinedDetailsArray[i][1];
            String amount = combinedDetailsArray[i][2];

            Program program;
            try {
                program = programDAO.exist(programId);
                System.out.println("ProgramDto: " + program);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ProgramDto programDto = new ProgramDto(program.getProgramId(), program.getProgramName(), program.getDuration(), program.getFee());

            double programFee = program.getFee();
            double totalDue = programFee - Double.parseDouble(amount);
            double installmentFee = switch (paymentOption) {
                case "3 inst" -> totalDue / 3;
                case "6 inst" -> totalDue / 6;
                case "12 inst" -> totalDue / 12;
                default -> totalDue;
            };

            StudentProgramDto studentProgramDto = new StudentProgramDto();
            studentProgramDto.setStudent(student);
            studentProgramDto.setProgram(programDto);
            studentProgramDto.setPayOption(paymentOption);
            studentProgramDto.setInstallmentFee(installmentFee);
            studentProgramDto.setTotalDue(totalDue);

            if (totalDue == 0) {
                studentProgramDto.setPayStatus("Paid");
            } else {
                studentProgramDto.setPayStatus("Not Paid");
            }

            studentProgramDtoList.add(studentProgramDto);
        }
        return studentProgramDtoList;
    }

    public String[][] combineProgramAndPaymentDetails(String[][] programDetailsArray, String[][] paymentDetailsArray) {
        int length = programDetailsArray.length;
        String[][] combinedDetailsArray = new String[length][3];

        for (int i = 0; i < length; i++) {
            String programId = programDetailsArray[i][0];
            String paymentOption = programDetailsArray[i][1];
            String amount = paymentDetailsArray[i][1];

            combinedDetailsArray[i][0] = programId;
            combinedDetailsArray[i][1] = paymentOption;
            combinedDetailsArray[i][2] = amount;
        }

        return combinedDetailsArray;
    }


    @Override
    public boolean updateStudentAndPrograms(StudentDto studentDto, List<String[]> programDetailsList, double total) {
        System.out.println("Updating Student and Programs: " + studentDto);

        StudentDto isExist = searchStudentByStudentId(studentDto.getStudentId());

        // Create the Student object from the DTO
        Student student = new Student(
                studentDto.getStudentId(),
                studentDto.getStudentNic(),
                studentDto.getDob(),
                studentDto.getFullName(),
                studentDto.getAddress(),
                studentDto.getEmail(),
                studentDto.getPhone(),
                isExist.getRegistrationDate(),
                isExist.getRegistrationTime()
        );

        List<StudentProgram> studentPrograms = new ArrayList<>();

        // Process each program from the program details list
        for (String[] programDetails : programDetailsList) {
            System.out.println("Processing Program Details: " + String.join(", ", programDetails));

            // Validate the structure of programDetails
            if (programDetails.length < 4) {
                System.out.println("Invalid program details format: " + String.join(", ", programDetails));
                continue;
            }

            // Extract payment option and payment type
            String payOption = "Unknown";
            String paymentType = "Unknown";
            String[] paymentParts = programDetails[2].split("\\("); // Split by '(' to separate payment type
            if (paymentParts.length >= 2) {
                payOption = paymentParts[0].trim(); // e.g., "6 inst"
                paymentType = paymentParts[1].replace(")", "").trim(); // e.g., "Installment"
            }

            // Debug: Log extracted payment details
            System.out.println("Pay Option: " + payOption);
            System.out.println("Payment Type: " + paymentType);

            // Only process programs with the "Advance" payment type
            if (!paymentType.equalsIgnoreCase("Advance\t:")) {

                // if this is not an advance payment, check the studentprogram records to see if it exists

                StudentProgram studentProgram = new StudentProgram();
                studentProgram = studentProgramDAO.exist(programDetails[0].trim(), studentDto.getStudentId());

                // if it exists, update the record
                String feeString = programDetails[3].replace("Rs.", "").trim();
                double installmentFee = Double.parseDouble(feeString);
                studentProgram.setInstallmentFee(installmentFee);

                // Calculate total due and set payment status
                double totalDue = studentProgram.getTotalDue() - installmentFee;

                System.out.println("\n\n===================\nTotal Due: " + totalDue);
                studentProgram.setTotalDue(totalDue);
                studentProgram.setPayStatus(totalDue <= 0 ? "Paid" : "Not Paid");

                studentPrograms.add(studentProgram);

                continue;
            }

            try {
                // Create a StudentProgram and associate it with the Student
                StudentProgram studentProgram = new StudentProgram();
                studentProgram.setStudent(student);

                // Fetch program details from the database
                Program program = programDAO.exist(programDetails[0]);
                if (program == null) {
                    System.out.println("Program not found for ID: " + programDetails[0]);
                    continue;
                }

                // Set program details
                studentProgram.setProgram(new Program(
                        program.getProgramId(),
                        program.getProgramName(),
                        program.getDuration(),
                        program.getFee()
                ));
                studentProgram.setPayOption(payOption);

                // Parse the installment fee
                String feeString = programDetails[3].replace("Rs.", "").trim();
                double installmentFee = Double.parseDouble(feeString);
                studentProgram.setInstallmentFee(installmentFee);

                // Calculate total due and set payment status
                double totalDue = program.getFee() - installmentFee;
                studentProgram.setTotalDue(totalDue);
                studentProgram.setPayStatus(totalDue <= 0 ? "Paid" : "Not Paid");

                // Debug: Log financial details
                System.out.println("Total Due: " + totalDue);
                System.out.println("Payment Status: " + studentProgram.getPayStatus());

                // Add to the list of student programs
                studentPrograms.add(studentProgram);
            } catch (NumberFormatException e) {
                System.out.println("Invalid fee format for program: " + programDetails[3]);
            } catch (Exception e) {
                throw new RuntimeException("Error processing program ID: " + programDetails[0], e);
            }
        }

        LoginController loginController = new LoginController();
        UserDto userDto = loginController.getLoginUser();

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setPaymentDescription("Payment");
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(java.time.LocalTime.now());
        payment.setAmount(total);
        payment.setUser(new User(
                userDto.getUserId(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getAddress(),
                userDto.getJobRole(),
                userDto.getUsername(),
                userDto.getPassword()
        ));

        boolean isUpdated = false;

        try {
            isUpdated = studentProgramTransDAO.updateStudentAndPrograms(student, studentPrograms, payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return isUpdated;
    }

}
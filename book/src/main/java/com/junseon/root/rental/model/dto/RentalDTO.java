package com.junseon.root.rental.model.dto;


import com.junseon.root.rental.model.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RentalDTO {
    private Long rentalId;
    private String BookTitle;
    private LocalDate rentalDate;
    private LocalDate deadline;
    private LocalDate returnDate;
    private RentalStatus status;

}

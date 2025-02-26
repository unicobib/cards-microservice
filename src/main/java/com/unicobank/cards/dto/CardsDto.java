package com.unicobank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(
        name = "Cards",
        description = "Schema to hold Cards Information"
)
@Getter@Setter@ToString
public class CardsDto {

    @Schema(
            description = "Mobile number of customer", example = "9234675897"
    )
    @NotEmpty(message = "Mobile number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Card number of Customer", example = "123456789321"
    )
    @NotEmpty(message = "Card number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card number must be 12 digits")
    private String cardNumber;

    @Schema(
            description = "Type of the card", example = "Credit Card"
    )
    @NotEmpty(message = "Card type should not be null or empty")
    private String cardType;

    @Schema(
            description = "total limit of the card", example = "10000"
    )
    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;

    @Schema(
            description = "Total amount used by customer", example = "1000"
    )
    @PositiveOrZero(message = "Total amount used should be greater than equals to zero")
    private int amountUsed;

    @Schema(
            description = "Total available amount against the card", example = "9000"
    )
    @PositiveOrZero(message = "Total available amount should be greater than equals to zero")
    private int availableAmount;
}

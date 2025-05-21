package nl.dannyj.mistral.models.ocr;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the dimensions of a PDF page's screenshot image.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRPageDimensions {

    /**
     * Dots per inch of the page-image.
     *
     * @return The DPI of the page image.
     */
    @NotNull
    @PositiveOrZero
    private Integer dpi;

    /**
     * Height of the image in pixels.
     *
     * @return The height of the image.
     */
    @NotNull
    @PositiveOrZero
    private Integer height;

    /**
     * Width of the image in pixels.
     *
     * @return The width of the image.
     */
    @NotNull
    @PositiveOrZero
    private Integer width;
}

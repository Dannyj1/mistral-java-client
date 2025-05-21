package nl.dannyj.mistral.models.ocr;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the OCR information for a single page.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRPageObject {

    /**
     * The page index in a pdf document starting from 0.
     *
     * @return The page index.
     */
    @NotNull
    @PositiveOrZero
    private Integer index;

    /**
     * The markdown string response of the page.
     *
     * @return The markdown string response.
     */
    @NotNull
    private String markdown;

    /**
     * List of all extracted images in the page.
     *
     * @return The list of extracted images.
     */
    @NotNull
    private List<OCRImageObject> images;

    /**
     * The dimensions of the PDF Page's screenshot image.
     *
     * @return The dimensions of the page.
     */
    @NotNull
    private OCRPageDimensions dimensions;
}

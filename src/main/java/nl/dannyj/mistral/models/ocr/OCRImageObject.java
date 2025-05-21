package nl.dannyj.mistral.models.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an extracted image object within an OCR page.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRImageObject {

    /**
     * Image ID for extracted image in a page.
     *
     * @return The image ID.
     */
    @NotNull
    private String id;

    /**
     * X coordinate of top-left corner of the extracted image.
     *
     * @return The top-left X coordinate.
     */
    @NotNull
    @PositiveOrZero
    @JsonProperty("top_left_x")
    private Integer topLeftX;

    /**
     * Y coordinate of top-left corner of the extracted image.
     *
     * @return The top-left Y coordinate.
     */
    @NotNull
    @PositiveOrZero
    @JsonProperty("top_left_y")
    private Integer topLeftY;

    /**
     * X coordinate of bottom-right corner of the extracted image.
     *
     * @return The bottom-right X coordinate.
     */
    @NotNull
    @PositiveOrZero
    @JsonProperty("bottom_right_x")
    private Integer bottomRightX;

    /**
     * Y coordinate of bottom-right corner of the extracted image.
     *
     * @return The bottom-right Y coordinate.
     */
    @NotNull
    @PositiveOrZero
    @JsonProperty("bottom_right_y")
    private Integer bottomRightY;

    /**
     * Base64 string of the extracted image.
     *
     * @return The Base64 image string.
     */
    @Nullable
    @JsonProperty("image_base64")
    private String imageBase64;
}

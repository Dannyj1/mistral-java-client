package nl.dannyj.mistral.models.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.Response;

import java.util.List;

/**
 * Represents the response body from the OCR API endpoint (`/v1/ocr`).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRResponse implements Response {

    /**
     * List of OCR info for pages.
     *
     * @return The list of OCR info for pages.
     */
    private List<OCRPageObject> pages;

    /**
     * The model used to generate the OCR.
     *
     * @return The model used to generate the OCR.
     */
    private String model;

    /**
     * Usage info for the OCR request.
     *
     * @return The usage info for the OCR request.
     */
    @JsonProperty("usage_info")
    private OCRUsageInfo usageInfo;
}

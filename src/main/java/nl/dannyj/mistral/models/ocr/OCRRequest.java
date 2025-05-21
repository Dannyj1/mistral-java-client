package nl.dannyj.mistral.models.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.Request;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.DocumentURLChunk;
import nl.dannyj.mistral.models.completion.content.ImageURLChunk;

import java.util.List;

/**
 * Represents the request body for the OCR API endpoint (`/v1/ocr`).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OCRRequest implements Request {

    /**
     * ID of the model to use.
     *
     * @param model The model's ID. Can't be null.
     * @return The model's ID.
     */
    @NotNull
    private String model;

    /**
     * Optional ID for the request.
     *
     * @param id The optional ID for the request.
     * @return The optional ID for the request.
     */
    private String id;

    /**
     * Document to run OCR on. Can be a DocumentURLChunk or an ImageURLChunk.
     *
     * @param document The document to run OCR on. Can be a {@link DocumentURLChunk} or an {@link ImageURLChunk}. Can't be null.
     * @return The document to run OCR on.
     */
    @NotNull
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = false)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = DocumentURLChunk.class, name = "document_url"),
            @JsonSubTypes.Type(value = ImageURLChunk.class, name = "image_url")
    })
    private ContentChunk document;

    /**
     * Specific pages user wants to process in various formats: single number, range, or list of both. Starts from 0.
     *
     * @param pages A list of specific page indices to process. Starts from 0. Null to process all pages.
     * @return A list of specific page indices to process.
     */
    private List<Integer> pages;

    /**
     * Include image URLs in response.
     *
     * @param includeImageBase64 Whether to include image URLs in the response. Null for default behavior.
     * @return Whether to include image URLs in the response.
     */
    @JsonProperty("include_image_base64")
    private Boolean includeImageBase64;

    /**
     * Maximum images to extract.
     *
     * @param imageLimit The maximum number of images to extract. Null for default behavior.
     * @return The maximum number of images to extract.
     */
    @JsonProperty("image_limit")
    private Integer imageLimit;

    /**
     * Minimum height and width of image to extract.
     *
     * @param imageMinSize The minimum height and width of images to extract. Null for default behavior.
     * @return The minimum height and width of images to extract.
     */
    @JsonProperty("image_min_size")
    private Integer imageMinSize;
}

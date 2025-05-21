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
 * Represents the usage information for an OCR request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OCRUsageInfo {

    /**
     * Number of pages processed.
     *
     * @return The number of pages processed.
     */
    @NotNull
    @PositiveOrZero
    @JsonProperty("pages_processed")
    private Integer pagesProcessed;

    /**
     * Document size in bytes.
     *
     * @return The document size in bytes.
     */
    @Nullable
    @PositiveOrZero
    @JsonProperty("doc_size_bytes")
    private Integer docSizeBytes;
}

package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** Sample */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T21:47:39.355598900+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class Sample {

  private String id;

  private String tenantId;

  private String receiptId;

  private String sampleCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sampleName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String model;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String specification;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String grade;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String brand;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String manufacturer;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String structuralPart;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String representQuantity;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sampleQuantity;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String batchNumber;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String supplyUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String arrivalDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String samplingDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String curingCondition;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String age;

  private Map<String, String> ext = new HashMap<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  private String createdAt;

  private String updatedAt;

  public Sample() {
    super();
  }

  /** Constructor with only required parameters */
  public Sample(
      String id,
      String tenantId,
      String receiptId,
      String sampleCode,
      Map<String, String> ext,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.tenantId = tenantId;
    this.receiptId = receiptId;
    this.sampleCode = sampleCode;
    this.ext = ext;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Sample id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  public Sample tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Get tenantId
   *
   * @return tenantId
   */
  @NotNull
  @Schema(name = "tenantId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenantId")
  public String getTenantId() {
    return tenantId;
  }

  @JsonProperty("tenantId")
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public Sample receiptId(String receiptId) {
    this.receiptId = receiptId;
    return this;
  }

  /**
   * Get receiptId
   *
   * @return receiptId
   */
  @NotNull
  @Schema(name = "receiptId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("receiptId")
  public String getReceiptId() {
    return receiptId;
  }

  @JsonProperty("receiptId")
  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public Sample sampleCode(String sampleCode) {
    this.sampleCode = sampleCode;
    return this;
  }

  /**
   * Get sampleCode
   *
   * @return sampleCode
   */
  @NotNull
  @Schema(name = "sampleCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sampleCode")
  public String getSampleCode() {
    return sampleCode;
  }

  @JsonProperty("sampleCode")
  public void setSampleCode(String sampleCode) {
    this.sampleCode = sampleCode;
  }

  public Sample sampleName(@Nullable String sampleName) {
    this.sampleName = sampleName;
    return this;
  }

  /**
   * Get sampleName
   *
   * @return sampleName
   */
  @Schema(name = "sampleName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sampleName")
  public @Nullable String getSampleName() {
    return sampleName;
  }

  @JsonProperty("sampleName")
  public void setSampleName(@Nullable String sampleName) {
    this.sampleName = sampleName;
  }

  public Sample model(@Nullable String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   *
   * @return model
   */
  @Schema(name = "model", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("model")
  public @Nullable String getModel() {
    return model;
  }

  @JsonProperty("model")
  public void setModel(@Nullable String model) {
    this.model = model;
  }

  public Sample specification(@Nullable String specification) {
    this.specification = specification;
    return this;
  }

  /**
   * Get specification
   *
   * @return specification
   */
  @Schema(name = "specification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("specification")
  public @Nullable String getSpecification() {
    return specification;
  }

  @JsonProperty("specification")
  public void setSpecification(@Nullable String specification) {
    this.specification = specification;
  }

  public Sample grade(@Nullable String grade) {
    this.grade = grade;
    return this;
  }

  /**
   * Get grade
   *
   * @return grade
   */
  @Schema(name = "grade", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grade")
  public @Nullable String getGrade() {
    return grade;
  }

  @JsonProperty("grade")
  public void setGrade(@Nullable String grade) {
    this.grade = grade;
  }

  public Sample brand(@Nullable String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   *
   * @return brand
   */
  @Schema(name = "brand", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("brand")
  public @Nullable String getBrand() {
    return brand;
  }

  @JsonProperty("brand")
  public void setBrand(@Nullable String brand) {
    this.brand = brand;
  }

  public Sample manufacturer(@Nullable String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  /**
   * Get manufacturer
   *
   * @return manufacturer
   */
  @Schema(name = "manufacturer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("manufacturer")
  public @Nullable String getManufacturer() {
    return manufacturer;
  }

  @JsonProperty("manufacturer")
  public void setManufacturer(@Nullable String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Sample structuralPart(@Nullable String structuralPart) {
    this.structuralPart = structuralPart;
    return this;
  }

  /**
   * Get structuralPart
   *
   * @return structuralPart
   */
  @Schema(name = "structuralPart", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("structuralPart")
  public @Nullable String getStructuralPart() {
    return structuralPart;
  }

  @JsonProperty("structuralPart")
  public void setStructuralPart(@Nullable String structuralPart) {
    this.structuralPart = structuralPart;
  }

  public Sample representQuantity(@Nullable String representQuantity) {
    this.representQuantity = representQuantity;
    return this;
  }

  /**
   * Get representQuantity
   *
   * @return representQuantity
   */
  @Schema(name = "representQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("representQuantity")
  public @Nullable String getRepresentQuantity() {
    return representQuantity;
  }

  @JsonProperty("representQuantity")
  public void setRepresentQuantity(@Nullable String representQuantity) {
    this.representQuantity = representQuantity;
  }

  public Sample sampleQuantity(@Nullable String sampleQuantity) {
    this.sampleQuantity = sampleQuantity;
    return this;
  }

  /**
   * Get sampleQuantity
   *
   * @return sampleQuantity
   */
  @Schema(name = "sampleQuantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sampleQuantity")
  public @Nullable String getSampleQuantity() {
    return sampleQuantity;
  }

  @JsonProperty("sampleQuantity")
  public void setSampleQuantity(@Nullable String sampleQuantity) {
    this.sampleQuantity = sampleQuantity;
  }

  public Sample batchNumber(@Nullable String batchNumber) {
    this.batchNumber = batchNumber;
    return this;
  }

  /**
   * Get batchNumber
   *
   * @return batchNumber
   */
  @Schema(name = "batchNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("batchNumber")
  public @Nullable String getBatchNumber() {
    return batchNumber;
  }

  @JsonProperty("batchNumber")
  public void setBatchNumber(@Nullable String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public Sample supplyUnit(@Nullable String supplyUnit) {
    this.supplyUnit = supplyUnit;
    return this;
  }

  /**
   * Get supplyUnit
   *
   * @return supplyUnit
   */
  @Schema(name = "supplyUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supplyUnit")
  public @Nullable String getSupplyUnit() {
    return supplyUnit;
  }

  @JsonProperty("supplyUnit")
  public void setSupplyUnit(@Nullable String supplyUnit) {
    this.supplyUnit = supplyUnit;
  }

  public Sample arrivalDate(@Nullable String arrivalDate) {
    this.arrivalDate = arrivalDate;
    return this;
  }

  /**
   * Get arrivalDate
   *
   * @return arrivalDate
   */
  @Schema(name = "arrivalDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("arrivalDate")
  public @Nullable String getArrivalDate() {
    return arrivalDate;
  }

  @JsonProperty("arrivalDate")
  public void setArrivalDate(@Nullable String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public Sample samplingDate(@Nullable String samplingDate) {
    this.samplingDate = samplingDate;
    return this;
  }

  /**
   * Get samplingDate
   *
   * @return samplingDate
   */
  @Schema(name = "samplingDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("samplingDate")
  public @Nullable String getSamplingDate() {
    return samplingDate;
  }

  @JsonProperty("samplingDate")
  public void setSamplingDate(@Nullable String samplingDate) {
    this.samplingDate = samplingDate;
  }

  public Sample curingCondition(@Nullable String curingCondition) {
    this.curingCondition = curingCondition;
    return this;
  }

  /**
   * Get curingCondition
   *
   * @return curingCondition
   */
  @Schema(name = "curingCondition", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("curingCondition")
  public @Nullable String getCuringCondition() {
    return curingCondition;
  }

  @JsonProperty("curingCondition")
  public void setCuringCondition(@Nullable String curingCondition) {
    this.curingCondition = curingCondition;
  }

  public Sample age(@Nullable String age) {
    this.age = age;
    return this;
  }

  /**
   * Get age
   *
   * @return age
   */
  @Schema(name = "age", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("age")
  public @Nullable String getAge() {
    return age;
  }

  @JsonProperty("age")
  public void setAge(@Nullable String age) {
    this.age = age;
  }

  public Sample ext(Map<String, String> ext) {
    this.ext = ext;
    return this;
  }

  public Sample putExtItem(String key, String extItem) {
    if (this.ext == null) {
      this.ext = new HashMap<>();
    }
    this.ext.put(key, extItem);
    return this;
  }

  /**
   * Get ext
   *
   * @return ext
   */
  @NotNull
  @Schema(name = "ext", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ext")
  public Map<String, String> getExt() {
    return ext;
  }

  @JsonProperty("ext")
  public void setExt(Map<String, String> ext) {
    this.ext = ext;
  }

  public Sample remark(@Nullable String remark) {
    this.remark = remark;
    return this;
  }

  /**
   * Get remark
   *
   * @return remark
   */
  @Schema(name = "remark", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remark")
  public @Nullable String getRemark() {
    return remark;
  }

  @JsonProperty("remark")
  public void setRemark(@Nullable String remark) {
    this.remark = remark;
  }

  public Sample createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   *
   * @return createdAt
   */
  @NotNull
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Sample updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   *
   * @return updatedAt
   */
  @NotNull
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updatedAt")
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Sample sample = (Sample) o;
    return Objects.equals(this.id, sample.id)
        && Objects.equals(this.tenantId, sample.tenantId)
        && Objects.equals(this.receiptId, sample.receiptId)
        && Objects.equals(this.sampleCode, sample.sampleCode)
        && Objects.equals(this.sampleName, sample.sampleName)
        && Objects.equals(this.model, sample.model)
        && Objects.equals(this.specification, sample.specification)
        && Objects.equals(this.grade, sample.grade)
        && Objects.equals(this.brand, sample.brand)
        && Objects.equals(this.manufacturer, sample.manufacturer)
        && Objects.equals(this.structuralPart, sample.structuralPart)
        && Objects.equals(this.representQuantity, sample.representQuantity)
        && Objects.equals(this.sampleQuantity, sample.sampleQuantity)
        && Objects.equals(this.batchNumber, sample.batchNumber)
        && Objects.equals(this.supplyUnit, sample.supplyUnit)
        && Objects.equals(this.arrivalDate, sample.arrivalDate)
        && Objects.equals(this.samplingDate, sample.samplingDate)
        && Objects.equals(this.curingCondition, sample.curingCondition)
        && Objects.equals(this.age, sample.age)
        && Objects.equals(this.ext, sample.ext)
        && Objects.equals(this.remark, sample.remark)
        && Objects.equals(this.createdAt, sample.createdAt)
        && Objects.equals(this.updatedAt, sample.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        tenantId,
        receiptId,
        sampleCode,
        sampleName,
        model,
        specification,
        grade,
        brand,
        manufacturer,
        structuralPart,
        representQuantity,
        sampleQuantity,
        batchNumber,
        supplyUnit,
        arrivalDate,
        samplingDate,
        curingCondition,
        age,
        ext,
        remark,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sample {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    receiptId: ").append(toIndentedString(receiptId)).append("\n");
    sb.append("    sampleCode: ").append(toIndentedString(sampleCode)).append("\n");
    sb.append("    sampleName: ").append(toIndentedString(sampleName)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    specification: ").append(toIndentedString(specification)).append("\n");
    sb.append("    grade: ").append(toIndentedString(grade)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("    structuralPart: ").append(toIndentedString(structuralPart)).append("\n");
    sb.append("    representQuantity: ").append(toIndentedString(representQuantity)).append("\n");
    sb.append("    sampleQuantity: ").append(toIndentedString(sampleQuantity)).append("\n");
    sb.append("    batchNumber: ").append(toIndentedString(batchNumber)).append("\n");
    sb.append("    supplyUnit: ").append(toIndentedString(supplyUnit)).append("\n");
    sb.append("    arrivalDate: ").append(toIndentedString(arrivalDate)).append("\n");
    sb.append("    samplingDate: ").append(toIndentedString(samplingDate)).append("\n");
    sb.append("    curingCondition: ").append(toIndentedString(curingCondition)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    ext: ").append(toIndentedString(ext)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}

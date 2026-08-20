package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.InspectionObjectParameterEntity;
import io.xr.lab.platform.entity.InspectionObjectStandardEntity;
import io.xr.lab.platform.entity.InspectionSpecialtyObjectEntity;
import io.xr.lab.platform.entity.InspectionStandardParameterEntity;
import io.xr.lab.platform.entity.enums.ObjectParameterKey;
import io.xr.lab.platform.entity.enums.ObjectReportNameKey;
import io.xr.lab.platform.entity.enums.ObjectStandardKey;
import io.xr.lab.platform.entity.enums.ParamInterfaceLinkKey;
import io.xr.lab.platform.entity.enums.ReportNameParameterKey;
import io.xr.lab.platform.entity.enums.ReportNameStandardKey;
import io.xr.lab.platform.entity.enums.SpecialtyObjectKey;
import io.xr.lab.platform.entity.enums.StandardParameterKey;
import io.xr.lab.platform.repository.InspectionObjectParameterRepository;
import io.xr.lab.platform.repository.InspectionObjectReportNameRepository;
import io.xr.lab.platform.repository.InspectionObjectStandardRepository;
import io.xr.lab.platform.repository.InspectionReportNameParameterRepository;
import io.xr.lab.platform.repository.InspectionReportNameStandardRepository;
import io.xr.lab.platform.repository.InspectionSpecialtyObjectRepository;
import io.xr.lab.platform.repository.InspectionStandardParameterRepository;
import io.xr.lab.platform.repository.ParamInterfaceLinkRepository;
import io.xr.lab.shared.dto.InspectionStandardRole;
import io.xr.lab.shared.dto.ObjectParameterLink;
import io.xr.lab.shared.dto.ObjectReportNameLink;
import io.xr.lab.shared.dto.ObjectStandardLink;
import io.xr.lab.shared.dto.ParamInterfaceLink;
import io.xr.lab.shared.dto.QualificationLevel;
import io.xr.lab.shared.dto.ReportNameParameterLink;
import io.xr.lab.shared.dto.ReportNameStandardLink;
import io.xr.lab.shared.dto.SpecialtyObjectLink;
import io.xr.lab.shared.dto.StandardParameterLink;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * M06 字典 8 个 junction link/unlink 操作单测（B6）。覆盖：
 *
 * <ul>
 *   <li>specialty ↔ object (I01-I02)
 *   <li>object ↔ parameter (I03-I04)
 *   <li>object ↔ standard with role (I05-I06)
 *   <li>standard ↔ parameter (I07-I08)
 *   <li>object ↔ report-name (I09-I10)
 *   <li>report-name ↔ standard with role (I11-I12)
 *   <li>report-name ↔ parameter (I13-I14)
 *   <li>parameter ↔ interface (I15-I16)
 * </ul>
 */
class InspectionJunctionServiceTest {

  private InspectionSpecialtyObjectRepository specialtyObjectRepo;
  private InspectionObjectParameterRepository objectParameterRepo;
  private InspectionObjectStandardRepository objectStandardRepo;
  private InspectionStandardParameterRepository standardParameterRepo;
  private InspectionObjectReportNameRepository objectReportNameRepo;
  private InspectionReportNameStandardRepository reportNameStandardRepo;
  private InspectionReportNameParameterRepository reportNameParameterRepo;
  private ParamInterfaceLinkRepository paramInterfaceLinkRepo;
  private InspectionJunctionService service;

  @BeforeEach
  void setUp() {
    specialtyObjectRepo = org.mockito.Mockito.mock(InspectionSpecialtyObjectRepository.class);
    objectParameterRepo = org.mockito.Mockito.mock(InspectionObjectParameterRepository.class);
    objectStandardRepo = org.mockito.Mockito.mock(InspectionObjectStandardRepository.class);
    standardParameterRepo = org.mockito.Mockito.mock(InspectionStandardParameterRepository.class);
    objectReportNameRepo = org.mockito.Mockito.mock(InspectionObjectReportNameRepository.class);
    reportNameStandardRepo = org.mockito.Mockito.mock(InspectionReportNameStandardRepository.class);
    reportNameParameterRepo =
        org.mockito.Mockito.mock(InspectionReportNameParameterRepository.class);
    paramInterfaceLinkRepo = org.mockito.Mockito.mock(ParamInterfaceLinkRepository.class);
    service =
        new InspectionJunctionService(
            specialtyObjectRepo,
            objectParameterRepo,
            objectStandardRepo,
            standardParameterRepo,
            objectReportNameRepo,
            reportNameStandardRepo,
            reportNameParameterRepo,
            paramInterfaceLinkRepo);
  }

  // === M06 specialty ↔ object ===

  @Test
  @Fn({"M06.F02.I05"})
  void linkSpecialtyObject_savesWithTimestamp() {
    when(specialtyObjectRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkSpecialtyObject(new SpecialtyObjectLink("S-1", "OBJ-1").remark("primary"));
    verify(specialtyObjectRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F02.I06"})
  void unlinkSpecialtyObject_existing_deletes() {
    when(specialtyObjectRepo.existsById(new SpecialtyObjectKey("S-1", "OBJ-1"))).thenReturn(true);
    service.unlinkSpecialtyObject(new SpecialtyObjectLink("S-1", "OBJ-1"));
    verify(specialtyObjectRepo, times(1)).deleteById(new SpecialtyObjectKey("S-1", "OBJ-1"));
  }

  @Test
  @Fn({"M06.F02.I06"})
  void unlinkSpecialtyObject_missing_throws404() {
    when(specialtyObjectRepo.existsById(any())).thenReturn(false);
    assertThrows(
        NoSuchElementException.class,
        () -> service.unlinkSpecialtyObject(new SpecialtyObjectLink("S-1", "OBJ-1")));
    verify(specialtyObjectRepo, never()).deleteById(any());
  }

  @Test
  @Fn({"M06.F02.I09"})
  void listSpecialtyObjectLinks_nullFilter_returnsAll() {
    var e1 = new InspectionSpecialtyObjectEntity();
    e1.setInspectionSpecialtyCode("S-1");
    e1.setInspectionObjectCode("OBJ-1");
    var e2 = new InspectionSpecialtyObjectEntity();
    e2.setInspectionSpecialtyCode("S-2");
    e2.setInspectionObjectCode("OBJ-2");
    when(specialtyObjectRepo.findAll()).thenReturn(java.util.List.of(e1, e2));
    var out = service.listSpecialtyObjectLinks(null);
    assertEquals(2, out.size());
    assertEquals("S-1", out.get(0).getInspectionSpecialtyCode());
    assertEquals("OBJ-2", out.get(1).getInspectionObjectCode());
  }

  @Test
  @Fn({"M06.F02.I09"})
  void listSpecialtyObjectLinks_filterByCode_returnsOnlyMatches() {
    var e1 = new InspectionSpecialtyObjectEntity();
    e1.setInspectionSpecialtyCode("S-1");
    e1.setInspectionObjectCode("OBJ-1");
    var e2 = new InspectionSpecialtyObjectEntity();
    e2.setInspectionSpecialtyCode("S-2");
    e2.setInspectionObjectCode("OBJ-2");
    when(specialtyObjectRepo.findAll()).thenReturn(java.util.List.of(e1, e2));
    var out = service.listSpecialtyObjectLinks("S-1");
    assertEquals(1, out.size());
    assertEquals("OBJ-1", out.get(0).getInspectionObjectCode());
  }

  // === M06 object ↔ parameter ===

  @Test
  @Fn({"M06.F02.I07"})
  void linkObjectParameter_savesWithDefaultQualification() {
    when(objectParameterRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkObjectParameter(
        new ObjectParameterLink("OBJ-1", "P-1", QualificationLevel.QUALIFIED));
    verify(objectParameterRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F02.I08"})
  void unlinkObjectParameter_existing_deletes() {
    when(objectParameterRepo.existsById(new ObjectParameterKey("OBJ-1", "P-1"))).thenReturn(true);
    service.unlinkObjectParameter("OBJ-1", "P-1");
    verify(objectParameterRepo, times(1)).deleteById(new ObjectParameterKey("OBJ-1", "P-1"));
  }

  @Test
  @Fn({"M06.F02.I08"})
  void unlinkObjectParameter_missing_throws404() {
    when(objectParameterRepo.existsById(any())).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.unlinkObjectParameter("OBJ-1", "P-1"));
  }

  @Test
  @Fn({"M06.F02.I10"})
  void listObjectParameterLinks_nullFilters_returnsAll() {
    var e1 = new InspectionObjectParameterEntity();
    e1.setInspectionObjectCode("OBJ-1");
    e1.setInspectionParameterCode("P-1");
    when(objectParameterRepo.findAll()).thenReturn(java.util.List.of(e1));
    var out = service.listObjectParameterLinks(null, null);
    assertEquals(1, out.size());
  }

  @Test
  @Fn({"M06.F02.I10"})
  void listObjectParameterLinks_filterByObjectCode_dropsOthers() {
    var e1 = new InspectionObjectParameterEntity();
    e1.setInspectionObjectCode("OBJ-1");
    e1.setInspectionParameterCode("P-1");
    var e2 = new InspectionObjectParameterEntity();
    e2.setInspectionObjectCode("OBJ-2");
    e2.setInspectionParameterCode("P-2");
    when(objectParameterRepo.findAll()).thenReturn(java.util.List.of(e1, e2));
    var out = service.listObjectParameterLinks("OBJ-1", null);
    assertEquals(1, out.size());
    assertEquals("P-1", out.get(0).getInspectionParameterCode());
  }

  // === M06 object ↔ standard (role) ===

  @Test
  @Fn({"M06.F01.I05"})
  void linkObjectStandard_savesWithRole() {
    when(objectStandardRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkObjectStandard(
        new ObjectStandardLink("OBJ-1", "GB/T-50082", InspectionStandardRole.TESTING));
    verify(objectStandardRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F01.I06"})
  void unlinkObjectStandard_existing_deletes() {
    when(objectStandardRepo.existsById(
            new ObjectStandardKey("OBJ-1", "GB/T-50082", InspectionStandardRole.TESTING)))
        .thenReturn(true);
    service.unlinkObjectStandard("OBJ-1", "GB/T-50082", InspectionStandardRole.TESTING);
    verify(objectStandardRepo, times(1))
        .deleteById(new ObjectStandardKey("OBJ-1", "GB/T-50082", InspectionStandardRole.TESTING));
  }

  @Test
  @Fn({"M06.F01.I06"})
  void unlinkObjectStandard_missing_throws404() {
    when(objectStandardRepo.existsById(any())).thenReturn(false);
    assertThrows(
        NoSuchElementException.class,
        () -> service.unlinkObjectStandard("OBJ-1", "GB/T", InspectionStandardRole.TESTING));
  }

  @Test
  @Fn({"M06.F01.I07"})
  void listObjectStandardLinks_filterByObjectCode_returnsOnlyMatches() {
    var e1 = new InspectionObjectStandardEntity();
    e1.setInspectionObjectCode("OBJ-1");
    e1.setInspectionStandardCode("GB/T-A");
    e1.setRole(InspectionStandardRole.TESTING);
    var e2 = new InspectionObjectStandardEntity();
    e2.setInspectionObjectCode("OBJ-2");
    e2.setInspectionStandardCode("GB/T-B");
    e2.setRole(InspectionStandardRole.TESTING);
    when(objectStandardRepo.findAll()).thenReturn(java.util.List.of(e1, e2));
    var out = service.listObjectStandardLinks("OBJ-1", null);
    assertEquals(1, out.size());
    assertEquals(InspectionStandardRole.TESTING, out.get(0).getRole());
  }

  // === M06 standard ↔ parameter ===

  @Test
  @Fn({"M06.F03.I05"})
  void linkStandardParameter_saves() {
    when(standardParameterRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkStandardParameter(new StandardParameterLink("GB/T-50082", "P-1"));
    verify(standardParameterRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F03.I06"})
  void unlinkStandardParameter_existing_deletes() {
    when(standardParameterRepo.existsById(new StandardParameterKey("GB/T-50082", "P-1")))
        .thenReturn(true);
    service.unlinkStandardParameter(new StandardParameterLink("GB/T-50082", "P-1"));
    verify(standardParameterRepo, times(1))
        .deleteById(new StandardParameterKey("GB/T-50082", "P-1"));
  }

  @Test
  @Fn({"M06.F03.I06"})
  void unlinkStandardParameter_missing_throws404() {
    when(standardParameterRepo.existsById(any())).thenReturn(false);
    assertThrows(
        NoSuchElementException.class,
        () -> service.unlinkStandardParameter(new StandardParameterLink("X", "Y")));
  }

  @Test
  @Fn({"M06.F03.I08"})
  void listStandardParameterLinks_nullFilters_returnsAll() {
    var e1 = new InspectionStandardParameterEntity();
    e1.setInspectionStandardCode("GB/T-A");
    e1.setInspectionParameterCode("P-1");
    when(standardParameterRepo.findAll()).thenReturn(java.util.List.of(e1));
    var out = service.listStandardParameterLinks(null, null);
    assertEquals(1, out.size());
    assertEquals("GB/T-A", out.get(0).getInspectionStandardCode());
  }

  // === M06 object ↔ report-name ===

  @Test
  @Fn({"M06.F07.I06"})
  void linkObjectReportName_saves() {
    when(objectReportNameRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkObjectReportName(new ObjectReportNameLink("OBJ-1", "RN-1"));
    verify(objectReportNameRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F04.I05"})
  void unlinkObjectReportName_existing_deletes() {
    when(objectReportNameRepo.existsById(new ObjectReportNameKey("OBJ-1", "RN-1")))
        .thenReturn(true);
    service.unlinkObjectReportName("OBJ-1", "RN-1");
    verify(objectReportNameRepo, times(1)).deleteById(new ObjectReportNameKey("OBJ-1", "RN-1"));
  }

  // === M06 report-name ↔ standard (role) ===

  @Test
  @Fn({"M06.F07.I07", "M06.F04.I07"})
  void linkReportNameStandard_saves() {
    when(reportNameStandardRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkReportNameStandard(
        new ReportNameStandardLink("RN-1", "GB/T-50082", InspectionStandardRole.JUDGMENT));
    verify(reportNameStandardRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F07.I07", "M06.F04.I07"})
  void unlinkReportNameStandard_existing_deletes() {
    when(reportNameStandardRepo.existsById(
            new ReportNameStandardKey("RN-1", "GB/T-50082", InspectionStandardRole.JUDGMENT)))
        .thenReturn(true);
    service.unlinkReportNameStandard("RN-1", "GB/T-50082", InspectionStandardRole.JUDGMENT);
    verify(reportNameStandardRepo, times(1))
        .deleteById(
            new ReportNameStandardKey("RN-1", "GB/T-50082", InspectionStandardRole.JUDGMENT));
  }

  // === M06 report-name ↔ parameter ===

  @Test
  @Fn({"M06.F07.I08", "M06.F03.I07"})
  void linkReportNameParameter_saves() {
    when(reportNameParameterRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    service.linkReportNameParameter(new ReportNameParameterLink("RN-1", "P-1"));
    verify(reportNameParameterRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F04.I06"})
  void unlinkReportNameParameter_existing_deletes() {
    when(reportNameParameterRepo.existsById(new ReportNameParameterKey("RN-1", "P-1")))
        .thenReturn(true);
    service.unlinkReportNameParameter("RN-1", "P-1");
    verify(reportNameParameterRepo, times(1)).deleteById(new ReportNameParameterKey("RN-1", "P-1"));
  }

  // === M06 parameter ↔ interface (config jsonb) ===

  @Test
  @Fn({"M06.F08.I06"})
  void linkParamInterface_savesWithSerializedConfig() {
    when(paramInterfaceLinkRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var body = new ParamInterfaceLink("P-1", "PI-1").putConfigItem("min", 0);
    service.linkParamInterface(body);
    verify(paramInterfaceLinkRepo, times(1)).save(any());
  }

  @Test
  @Fn({"M06.F03.I07"})
  void unlinkParamInterface_existing_deletes() {
    when(paramInterfaceLinkRepo.existsById(new ParamInterfaceLinkKey("P-1", "PI-1")))
        .thenReturn(true);
    service.unlinkParamInterface("P-1", "PI-1");
    verify(paramInterfaceLinkRepo, times(1)).deleteById(new ParamInterfaceLinkKey("P-1", "PI-1"));
  }

  @Test
  @Fn({"M06.F03.I07"})
  void unlinkParamInterface_missing_throws404() {
    when(paramInterfaceLinkRepo.existsById(any())).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.unlinkParamInterface("P-1", "PI-1"));
  }

  // === helpers ===

  /** Keep IDE happy about QualificationLevel — used in ObjectParameterLink signature above. */
  @SuppressWarnings("unused")
  private static QualificationLevel refQl() {
    return QualificationLevel.QUALIFIED;
  }

  @SuppressWarnings("unused")
  private static Map<String, Object> refConfig() {
    return Map.of("k", "v");
  }
}

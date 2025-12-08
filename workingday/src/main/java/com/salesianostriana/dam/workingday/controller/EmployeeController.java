package com.salesianostriana.dam.workingday.controller;

import com.salesianostriana.dam.workingday.dto.CreateEmployeeCmd;
import com.salesianostriana.dam.workingday.dto.EditEmployeeCmd;
import com.salesianostriana.dam.workingday.dto.EmployeeResponse;
import com.salesianostriana.dam.workingday.dto.SigningResponse;
import com.salesianostriana.dam.workingday.exception.IllegalArgumentException;
import com.salesianostriana.dam.workingday.exception.NotFoundException;
import com.salesianostriana.dam.workingday.exception.SigningDuplicateException;
import com.salesianostriana.dam.workingday.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(name = "Empleados", description = "Controlador de los empleados")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Obtener todos los empleados de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se han mostrado correctamente los empleados",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeResponse.class)),
                            examples = @ExampleObject("""
                                    [
                                        {
                                            "id": 1,
                                            "fullName": "Álvaro Lázaro Castellón",
                                            "position": "junior",
                                            "salary": 2000.00,
                                            "departmentName": "Aún no está en ningún departamento",
                                            "signings": []
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se han encontrado empleados en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "No existen empleados en la base de datos",
                                        "instance": "/api/v1/employee"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping
    public List<EmployeeResponse> listAll() {
        return employeeService.findAll()
                .stream()
                .map(EmployeeResponse::of)
                .toList();
    }

    @Operation(summary = "Guardar empleado en la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Se ha guardado correctamente el empleado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "id": 1,
                                      "fullName": "Pepito Juarez Gutierrez",
                                      "position": "junior",
                                      "salary": 1000,
                                      "departmentName": "Aún no está en ningún departamento",
                                      "signings": []
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Hay datos introducidos incorrectamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IllegalArgumentException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/data-error",
                                        "title": "Error de datos",
                                        "status": 400,
                                        "detail": "Error al crear/editar la entidad",
                                        "instance": "/api/v1/employee"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo de datos a introducir en el JSON",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateEmployeeCmd.class),
                            examples = @ExampleObject("""
                                    {
                                      "fullName": "Pepito Juarez Gutierrez",
                                      "position": "junior",
                                      "salary": 2000,
                                      "departmentId": 1
                                    }
                                    """)
                    ),
                    required = true
            )
            @RequestBody CreateEmployeeCmd cmd
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmployeeResponse.of(employeeService.save(cmd)));
    }

    @Operation(summary = "Obtener un empleado de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se ha mostrado correctamente el empleado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "id": 1,
                                      "fullName": "Pepito Juarez Gutierrez",
                                      "position": "junior",
                                      "salary": 1000,
                                      "departmentName": "Finanzas",
                                      "signings": []
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado al empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El empleado con el id: 2, no existe.",
                                        "instance": "/api/v1/employee/2"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/{id:[0-9]+}")
    public EmployeeResponse getById(
            @Parameter(description = "ID del empleado a buscar", required = true)
            @PathVariable Long id
    ) {
        return EmployeeResponse.of(employeeService.findById(id));
    }

    @Operation(summary = "Editar un empleado de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se ha editado correctamente el empleado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "fullName": "Pepito Juarez Gutierrez",
                                        "position": "junior",
                                        "salary": 1000,
                                        "departmentName": "Finanzas",
                                        "signings": []
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Hay datos introducidos incorrectamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IllegalArgumentException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/data-error",
                                        "title": "Error de datos",
                                        "status": 400,
                                        "detail": "Error al crear/editar la entidad",
                                        "instance": "/api/v1/employee/1/department/1"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El empleado con el id: 2, no existe.",
                                        "instance": "/api/v1/employee/2"
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/{employeeId:[0-9]+}/department/{departmentId:[0-9]+}")
    public ResponseEntity<EmployeeResponse> editEmployee(
            @Parameter(description = "ID del empleado a editar", required = true)
            @PathVariable Long employeeId,
            @Parameter(description = "ID del departamento a registrar el empleado", required = true)
            @PathVariable Long departmentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo de datos a introducir en el JSON",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EditEmployeeCmd.class),
                            examples = @ExampleObject("""
                                    {
                                        "fullName": "Pepito Juarez Gutierrez",
                                        "position": "junior",
                                        "salary": 2000
                                    }
                                    """)
                    ),
                    required = true
            )
            @RequestBody EditEmployeeCmd cmd
    ) {
        return ResponseEntity.ok(EmployeeResponse.of(employeeService.edit(employeeId, cmd, departmentId)));
    }

    @Operation(summary = "Fichar un empleado de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Se ha fichado correctamente el empleado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SigningResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "moment": "2025-11-27T00:08:20.3851378",
                                        "type": "EXIT"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El empleado con el id: 2, no existe.",
                                        "instance": "/api/v1/employee/2/signing"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "No se puede introducir el mismo fichaje de forma seguida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SigningDuplicateException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/duplicate-signing",
                                        "title": "Fichaje duplicado",
                                        "status": 409,
                                        "detail": "No puedes fichar del mismo tipo dos veces",
                                        "instance": "/api/v1/employee/1/signing"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/{id:[0-9]+}/signing")
    public ResponseEntity<SigningResponse> createSigning(
            @Parameter(description = "ID del empleado a fichar", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SigningResponse.of(employeeService.setSigning(id)));
    }

    @Operation(summary = "Obtener todos los fichajes de un empleado de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se han mostrado correctamente los fichajes de ese empleado",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SigningResponse.class)),
                            examples = @ExampleObject("""
                                    [
                                        {
                                            "moment": "2025-11-27T00:11:22.002563",
                                            "type": "EXIT"
                                        },
                                        {
                                            "moment": "2025-11-27T00:11:22.002563",
                                            "type": "ENTRY"
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado el empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El empleado con el id: 2, no existe.",
                                        "instance": "/api/v1/employee/2/signing"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se han encontrado fichajes de ese empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "No existen fichajes por parte de este empleado",
                                        "instance": "/api/v1/employee/1/signing"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/{id:[0-9]+}/signing")
    public List<SigningResponse> getEmployeeSignings(
            @Parameter(description = "ID del empleado a ver sus fichajes", required = true)
            @PathVariable Long id
    ) {
        return employeeService.listSigning(id).stream()
                .map(SigningResponse::of)
                .toList();
    }

    @Operation(summary = "Eliminar a un empleado de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Se ha eliminado correctamente al empleado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = @ExampleObject("""
                                    {}
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado al empleado en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El empleado con el id: 2, no existe.",
                                        "instance": "/api/v1/employee/2"
                                    }
                                    """)
                    )
            )
    })
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> deleteEmployee(
            @Parameter(description = "ID del empleado a eliminar", required = true)
            @PathVariable Long id
    ) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

package com.salesianostriana.dam.workingday.controller;

import com.salesianostriana.dam.workingday.dto.CreateDepartmentCmd;
import com.salesianostriana.dam.workingday.dto.DepartmentResponse;
import com.salesianostriana.dam.workingday.dto.EmployeeResponse;
import com.salesianostriana.dam.workingday.exception.IllegalArgumentException;
import com.salesianostriana.dam.workingday.exception.NotFoundException;
import com.salesianostriana.dam.workingday.service.DepartmentService;
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
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
@Tag(name = "Departamentos", description = "Controlador de los departamentos")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Obtener todos los departamentos de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se han mostrado correctamente los departamentos",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartmentResponse.class)),
                            examples = @ExampleObject("""
                                    [
                                         {
                                             "id": 1,
                                             "name": "Finanzas",
                                             "budget": 20000.00,
                                             "employees": []
                                         }
                                     ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se han encontrado departamentos en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "No existen empleados en la base de datos",
                                        "instance": "/api/v1/department"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping
    public List<DepartmentResponse> getAll() {
        return departmentService.findAll().stream()
                .map(DepartmentResponse::of)
                .toList();
    }

    @Operation(summary = "Guardar departamento en la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Se ha guardado correctamente el departamento",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "name": "Finanzas",
                                        "budget": 20000,
                                        "employees": []
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
                                        "instance": "/api/v1/department"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo de datos a introducir en el JSON",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateDepartmentCmd.class),
                            examples = @ExampleObject("""
                                    {
                                        "name": "Finanzas",
                                        "budget": 20000
                                    }
                                    """)
                    ),
                    required = true
            )
            @RequestBody CreateDepartmentCmd cmd
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DepartmentResponse.of(departmentService.save(cmd)));
    }

    @Operation(summary = "Obtener un departamento de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se ha mostrado correctamente el departamento",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "name": "Finanzas",
                                        "budget": 20000,
                                        "employees": []
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se ha encontrado el departamento en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El departamento con el id: 1, no existe.",
                                        "instance": "/api/v1/department/1"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/{id:[0-9]+}")
    public DepartmentResponse getById(
            @Parameter(description = "ID del departamento a buscar", required = true)
            @PathVariable Long id
    ) {
        return DepartmentResponse.of(departmentService.findById(id));
    }

    @Operation(summary = "Editar un departamento de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se ha editado correctamente el departamento",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "name": "Finanzas",
                                        "budget": 20000,
                                        "employees": []
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
                                        "instance": "/api/v1/department/1"
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
                                        "detail": "El departamento con el id: 1, no existe.",
                                        "instance": "/api/v1/department/1"
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<DepartmentResponse> editDepartment(
            @Parameter(description = "ID del departamento a editar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo de datos a introducir en el JSON",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateDepartmentCmd.class),
                            examples = @ExampleObject("""
                                    {
                                        "name": "Finanzas",
                                        "budget": 20000
                                    }
                                    """)
                    ),
                    required = true
            )
            @RequestBody CreateDepartmentCmd cmd
    ) {
        return ResponseEntity.ok(DepartmentResponse.of(departmentService.edit(id, cmd)));
    }

    @Operation(summary = "Eliminar un departamento de la base de datos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Se ha eliminado correctamente el departamento",
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
                    description = "No se ha encontrado el departamento en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class),
                            examples = @ExampleObject("""
                                    {
                                        "type": "https://dam.salesianos-triana.com/entity-not-found",
                                        "title": "Entidad no encontrada",
                                        "status": 404,
                                        "detail": "El departamento con el id: 1, no existe.",
                                        "instance": "/api/v1/department/1"
                                    }
                                    """)
                    )
            )
    })
    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> deleteDepartment(
            @Parameter(description = "ID del departamento a eliminar", required = true)
            @PathVariable Long id
    ) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

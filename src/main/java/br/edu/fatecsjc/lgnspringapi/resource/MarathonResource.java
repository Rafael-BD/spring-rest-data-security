package br.edu.fatecsjc.lgnspringapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/marathon")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Marathons")
@SecurityRequirement(name = "bearerAuth")
public class MarathonResource {
    @Autowired
    private MarathonService marathonService;

    @GetMapping  
    @Operation(
            description = "Get all marathons",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<List<MarathonDTO>> getAllMarathons() {
        return ResponseEntity.ok(marathonService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get a marathon by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<MarathonDTO> getMarathonById(@PathVariable Long id) {
        return ResponseEntity.ok(marathonService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(
            description = "Update a marathon by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<MarathonDTO> update(@PathVariable Long id, @RequestBody MarathonDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marathonService.save(id, body));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(
            description = "Register a new marathon",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<MarathonDTO> register(@RequestBody MarathonDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marathonService.save(body));
    }

    @DeleteMapping ("/{id}")
    @Operation(
            description = "Delete a marathon by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marathonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
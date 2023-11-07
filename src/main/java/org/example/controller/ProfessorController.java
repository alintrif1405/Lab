package org.example.controller;


import jakarta.persistence.EntityNotFoundException;
import org.example.model.Professors;
import org.example.model.Students;
import org.example.service.ProfessorService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professors> getAllProfessors(){
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professors> getProfessorById(@PathVariable Integer id){
        Professors professors = professorService.getProfessorById(id).orElse(null);
        return (professors!=null) ? ResponseEntity.ok(professors):ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<?> addProfessor(@RequestBody Professors professor) {
        {
                Professors addedProfessor = professorService.addProfessor(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProfessor);

        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable Integer id) {
        try {
            professorService.deleteProfessor(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

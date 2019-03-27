
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.model.Hero;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {

    /** ヒーローのリスト */
    private List<Hero> heroes;

    public HeroController() {
        this.heroes = new ArrayList<>();
        heroes.add(new Hero(11, "Mr. Nice"));
        heroes.add(new Hero(12, "Narco"));
        heroes.add(new Hero(13, "Bombasto"));
        heroes.add(new Hero(14, "Celeritas"));
        heroes.add(new Hero(15, "Magneta"));
        heroes.add(new Hero(16, "RubberMan"));
        heroes.add(new Hero(17, "Dynama"));
        heroes.add(new Hero(18, "Dr IQ"));
        heroes.add(new Hero(19, "Magma"));
        heroes.add(new Hero(20, "Tornado"));
    }

    /**
     * angular tutorial
     * reference app/hero.service.ts
     * @param id
     * @return Hero
     */
    @GetMapping("/{id}")
    public Hero getHero(@PathVariable Integer id) {
        Map<Integer,Hero> heroMap = heroes.stream().collect(Collectors.toMap(Hero::getId, Function.identity()));
        Hero hero = heroMap.get(id);
        return hero;
    }

    /**
     * angular tutorial
     *  reference app/hero.service.ts
     * @return hero array
     */
    @GetMapping("/")
    public List<Hero> getHeroes() {
        return heroes;
    }

    
    /**
     * angular tutorial
     * reference app/hero.service.ts
     * @param newHero
     * @return Hero
     */
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Hero create(@RequestBody  Hero newHero) {
        Optional<Integer> max = heroes.stream().map(hero -> hero.getId()).max(Comparator.naturalOrder());
        Integer id = max.get() + 1;
        newHero.setId(id);
        heroes.add(newHero);
        return newHero;
    }

    /**
     * angular tutorial
     * reference app/hero.service.ts
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        heroes.remove(heroes.stream().filter(hero -> hero.getId() == id).findFirst().get());
    }

    /**
     * angular tutorial
     * reference app/hero-search.service.ts
     * @param name
     * @return selected heroes
     */
    @GetMapping("/app/heroes")
    public List<Hero> search(@RequestParam  String name) {
        List<Hero> selectedHeroes = heroes.stream()
                .filter(hero -> hero.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return selectedHeroes;
    }
}
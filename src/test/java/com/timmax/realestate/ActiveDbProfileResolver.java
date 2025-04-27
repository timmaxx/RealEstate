package com.timmax.realestate;

import org.springframework.lang.NonNull;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

import java.util.Arrays;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver extends DefaultActiveProfilesResolver {
    @Override
    public @NonNull
    String[] resolve(@NonNull Class<?> aClass) {
        // https://stackoverflow.com/a/52438829/548473
        // super.resolve(aClass) Возвращает массив строк из 0 элементов.
        // Зачем его вызывать?
        String[] activeProfiles = super.resolve(aClass);
        // А мы делаем массив из 1 элемента.
        String[] activeProfilesWithDb = Arrays.copyOf(activeProfiles, activeProfiles.length + 1);
        // И в единственный элемент сохраняем имя профиля, полученного из Profiles.getActiveDbProfile()
        activeProfilesWithDb[activeProfiles.length] = Profiles.getActiveDbProfile();
        return activeProfilesWithDb;
    }
/*
    String[] resolve(@NonNull Class<?> aClass) {
        // https://stackoverflow.com/a/52438829/548473
        String[] activeProfiles = super.resolve(aClass);
        System.out.println("  activeProfiles = " + activeProfiles);
        System.out.println("  activeProfiles.length = " + activeProfiles.length);
        for (int i = 0; i < activeProfiles.length; i++) {
            System.out.println("    activeProfiles[i] = " + activeProfiles[i]);
        }
        String[] activeProfilesWithDb = Arrays.copyOf(activeProfiles, activeProfiles.length + 1);
        System.out.println("  activeProfilesWithDb = " + activeProfilesWithDb);
        System.out.println("  activeProfilesWithDb.length = " + activeProfilesWithDb.length);
        for (int i = 0; i < activeProfilesWithDb.length; i++) {
            System.out.println("    activeProfilesWithDb[i] = " + activeProfilesWithDb[i]);
        }
        activeProfilesWithDb[activeProfiles.length] = Profiles.getActiveDbProfile();
        System.out.println("  activeProfilesWithDb = " + activeProfilesWithDb);
        System.out.println("  activeProfilesWithDb.length = " + activeProfilesWithDb.length);
        for (int i = 0; i < activeProfilesWithDb.length; i++) {
            System.out.println("    activeProfilesWithDb[i] = " + activeProfilesWithDb[i]);
        }
        return activeProfilesWithDb;
    }
*/
}

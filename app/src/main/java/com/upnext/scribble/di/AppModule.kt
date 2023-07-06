package com.upnext.scribble.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.upnext.scribble.data.repository.IAuthRepository
import com.upnext.scribble.data.repository.IBoardRepository
import com.upnext.scribble.data.repository.IUserRepository
import com.upnext.scribble.data.repository.IWorkspaceRepository
import com.upnext.scribble.domain.use_case.auth_use_cases.GetSignedInUserUIDUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideStorage() = FirebaseStorage.getInstance().reference

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ) = IAuthRepository(firebaseFirestore, firebaseAuth)

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore
    ) = IUserRepository(firebaseFirestore)

    @Provides
    @Singleton
    fun provideWorkspaceRepository(
        firebaseFirestore: FirebaseFirestore,
        auth: FirebaseAuth,
        storage: StorageReference
    ) = IWorkspaceRepository(firebaseFirestore, storage, auth)

    @Provides
    @Singleton
    fun provideUIDUseCase(auth: FirebaseAuth) = GetSignedInUserUIDUseCase(auth)

    @Provides
    @Singleton
    fun provideBoardRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ) = IBoardRepository(firebaseFirestore, firebaseAuth)

}
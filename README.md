# ConsumeApiRetrofit-part1-
the objective of this workshop is to consume  API get using Retrofi2

https://tyradex.tech/api/v1/pokemon 

acceede to this api from navigator or Postman and extract model of this API

# Step 1 
Create Jetpack Android Project
Add Retrofit and Gson dependency to your gradle
```kotlin
// Retrofit
implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation ("com.google.code.gson:gson:2.11.0")
```
> __retrofit2-kotlinx-serialization-converter__: A converter for Retrofit to use Kotlinx Serialization.

> __retrofit__: A type-safe HTTP client for making API requests.

> __gson__: A library for converting Java objects to/from JSON.
Add coil and serialization dependency
```kotlin
// Coil
implementation("io.coil-kt:coil-compose:2.4.0")
//Serialization
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
```
> __coil-compose__: An image loading library for Jetpack Compose, allowing easy image loading and caching in Android apps.
> __kotlinx-serialization-json__: A Kotlin library for serializing and deserializing JSON data using Kotlinx Serialization.
# Step 2 
Add a package named model under your main package, in this create a model named Pockemon.kt using POJO Pluguin (install it if it is not installed)
> 1. Install the POJO Plugin
  Open Android Studio.
  Go to File > Settings (on macOS: Android Studio > Preferences).
  In the left sidebar, select Plugins.
  Click the Marketplace tab.
  In the search bar, type POJO Generator or POJO Plugin.
  Click Install next to the plugin by Vasiliy (or another POJO generator you prefer).
  After installation, click Restart IDE to activate the plugin.
> 2. Using the POJO Plugin
  Open the Java/Kotlin file where you want to generate your POJO.
  Select or copy the JSON data that you want to generate POJO classes from.
  Right-click inside the file, then choose POJO Generator (or the relevant context menu for your plugin).

# Step 3
Create a package named network in your main package
in this package create an interface named ApiService containing the access to api
```kotlin
interface ApiService{
  @GET("pokemon")
    suspend fun  getAllPokemon():List<PockemonsItem>
}
```

in the same file add the BASE_URL and the retrofit instance
```kotlin
private var BASE_URL="https://tyradex.tech/api/v1/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()
```

add also the singleton that instantiate the singleton retrofit in the same package network
```kotlin
object PockApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }}
```

# Step 4

create a package named viewmodel in witch create a file named ViewModel.kt
declare Sealed Class PockUiState

```kotlin
sealed interface PockUiState {
    data class Success(val pokemons: List<PockemonsItem>) : PockUiState
    object Error : PockUiState
    object Loading : PockUiState
}
```

create the class ViewModel and the method getAllPockemons and declare state variable pockUIState
```kotlin
class ViewModel:ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var pockUiState: PockUiState by mutableStateOf(PockUiState.Loading)
        private set

    /**
     */
    init {
        getAllPock()
    }
     fun getAllPock() {
        viewModelScope.launch {

            pockUiState = try {
                val listResult = PockApi.retrofitService.getAllPokemon()
                PockUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                PockUiState.Error
            } catch (e: HttpException) {
                PockUiState.Error
            }
        }
    }
}
```

# Step 5 

create package view, in witch create Home Screens Composable that use viewmodel to display the list of pokemons , error if error and loading if loading
```kotlin
@Composable
fun HomeScreen(
    pokUiState: PockUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (pokUiState) {
        is PockUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PockUiState.Success -> PhotosGridScreen(
            pokUiState.pokemons, modifier = modifier.fillMaxWidth()
        )
        is PockUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */


@Composable
fun PhotosGridScreen(
    pockemons: List<PockemonsItem?>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        if (pockemons != null) {
            items(items = pockemons.map { it }) { pockemon ->
                if (pockemon != null) {
                    PockPhotoCard(
                        pockemon,
                        modifier = modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun PockPhotoCard(pockemon: PockemonsItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(pockemon.sprites?.regular)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.pockphoto),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

# Step 6
Prepare the Composable PokApp that contain the main composable of the app
```kotlin
@Composable
fun PokApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PokTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val pokViewModel: PokViewModel = viewModel()
            HomeScreen(
                pokUiState = pokViewModel.pockUiState,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
```

# Step 7
call your Pokapp in your main activity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitGet1Theme {
                    PokApp()
                
            }
        }
    }
}
```


INSERT INTO users (first_name, last_name, email, username, password, is_blocked, is_admin)
VALUES
    ('Deleted', 'User', 'deletedUser@example.com', 'deletedUser', 'deletedUser', 0, 0),
    ('Simona', 'Nedeva', 'simona@example.com', 'simona_nedeva', 'password123', 0, 1),
    ('Dora', 'Dimitrov', 'dora@example.com', 'dora_dimitrov', 'securepass', 0, 1),
    ('Marian', 'Maximov', 'marian@example.com', 'marian_maximov', 'pass123', 0, 1),
    ('Ivan', 'Ivanov', 'ivan_ivanov@example.com', 'ivan_ivanov', 'passJbl', 0, 0),
    ('Maria', 'Aleksandrova', 'maria_aleksandrova@example.com', 'marialex', '123456', 0, 0),
    ('Petra', 'Georgieva', 'petra_g@example.com', 'petrag', 'thisIsPetra', 0, 0),
    ('Mimi', 'Ivanova', 'mimii@example.com', 'mimii', '124579', 0, 0),
    ('Vladimir', 'Bolonov', 'vlado92@example.com', 'vlado92', 'vLaDo9_2', 0, 0),
    ('Zdravko', 'Neskov', 'neskov@example.com', 'z_neskov', 'le_5tge', 0, 0),
    ('Elena', 'Petrova', 'elena@example.com', 'elena_petrova', 'xlmngt', 0, 0),
    ('Stefan', 'Dimitrov', 'stefan@example.com', 'stefan_d', 'stefanPass', 0, 0),
    ('Nina', 'Koleva', 'nina@example.com', 'nina_k', 'nina98', 0, 0),
    ('Alex', 'Stefanov', 'alex@example.com', 'alex_s', 'balletAlex123', 0, 0),
    ('Natalia', 'Dobreva', 'natalia@example.com', 'natalia_d', 'nataliaPass', 0, 0),
    ('Dimitar', 'Petrov', 'dimitar@example.com', 'dimitar_p', 'dimitarPass123', 0, 0),
    ('Silvia', 'Georgieva', 'silvia@example.com', 'silvia_g', 'silvia98Pass', 0, 0),
    ('Stoyan', 'Stoyanov', 'stoyan@example.com', 'stoyan_s', 'stoyan12Pass', 0, 0),
    ('Iveta', 'Ivanova', 'iveta@example.com', 'iveta_i', 'ivetaPass12345', 0, 0),
    ('Georgi', 'Georgiev', 'georgi@example.com', 'georgi_g', 'georgiPassSecure@', 0, 0),
    ('Marina', 'Shostakovic', 'marinash@example.com', 'marina_bab', 'marinaIs@', 1, 0),
    ('Miroslav', 'Spasov', 'miro23@example.com', 'miro_23', 'miroooO@', 1, 0),
    ('Dimitar', 'Ivanov', 'dimcho_ivanov@example.com', 'dimooo', 'd2insl@', 1, 0);

INSERT INTO phone_numbers (phone_number, user_id)
VALUES
    ('0889199956', 1),
    ('0889199955', 2),
    ('0889199954', 3);

INSERT INTO posts (title, content, user_id, time_stamp)
VALUES
    ('Vienna waits for you!',
     'Vienna - a city that embraced me with its history and contemporary allure. From imperial grandeur to the melodies in the air, each moment felt like a chapter in a captivating novel.',
     1, '2023-10-15 12:34:56'),

    ('Belgrade Chronicles!',
     'Belgrade - a city pulsating with energy and history. From its resilient spirit to the eclectic blend of ancient fortresses and vibrant nightlife, every corner tells a story of resilience and cultural richness.',
     2, '2023-10-16 08:45:23'),

    ('Plovdiv old town Charms!',
     'Plovdiv - a city where ancient history meets modern charm. From the cobbled streets of the Old Town to the artistic vibe of Kapana, every step unfolds a captivating blend of culture and timeless beauty.',
     3, '2023-10-17 15:20:10'),

    ('Tokyo Dreams - the city from the future!',
     'Tokyo - a city where ancient traditions harmonize with futuristic innovation. From the serene temples to the bustling streets of Shibuya, each moment is a glimpse into a captivating juxtaposition.',
     4, '2023-10-18 21:45:32'),

    ('Parisian Rhapsody - the spirit of fashion and wine!',
     'Paris - a city of love, art, and timeless elegance. From the iconic Eiffel Tower to the charming cobblestone streets of Montmartre, every corner whispers tales of romance and cultural richness.',
     5, '2023-10-19 14:12:09'),

    ('Concrete Jungle Chronicles - NYC',
     'New York City - a metropolis that never sleeps, where towering skyscrapers and diverse neighborhoods create a vibrant mosaic of culture. From Central Park to the neon lights of Times Square, every heartbeat is a pulse of energy.',
     1, '2023-10-20 10:56:47'),

    ('Cape Town Tales - Wild, Fun!',
     'Cape Town - where the azure oceans meet the majestic Table Mountain. From the historic Robben Island to the colorful Bo-Kaap district, every vista is a testament to nature beauty and a rich cultural tapestry.',
     6, '2023-10-21 18:30:15'),

    ('Sydney Serenade - like a dream!',
     'Sydney - a city of sun, surf, and iconic landmarks. From the Sydney Opera House to the golden beaches of Bondi, each sunrise paints a new chapter in this harbor city’s story.',
     2, '2023-10-22 07:23:54'),

    ('Eternal City Impressions - history comes to life!',
     'Rome - where ancient history lives in harmony with modern life. From the Colosseum grandeur to the quaint streets of Trastevere, each moment is a step through centuries of art culture and architecture.',
     1, '2023-10-23 16:40:28'),

    ('Istanbul Odyssey - where west and east meet!',
     'Istanbul - a city straddling two continents, where East meets West. From the domes of Hagia Sophia to the bustling Grand Bazaar, each sight is a journey through the rich tapestry of Turkish history and culture.',
     3, '2023-10-24 12:15:37'),

    ('Rio Rhythms - wild and beautiful!',
     'Rio de Janeiro - where the rhythm of samba meets the beauty of golden beaches. From the iconic Christ the Redeemer to the lively streets of Copacabana, every day is a celebration of life and vibrant colors.',
     7, '2023-10-25 09:08:42'),

    ('Bangkok Reverie - crazy, fun, spectacular!',
     'Bangkok - a city of contrasts, where ancient temples stand beside modern skyscrapers. From the bustling street markets to the serene Wat Arun, each moment is a blend of tradition and the contemporary.',
     5, '2023-10-26 14:55:19'),

    ('Barcelona Journeys - so much fun, art, beaches!',
     'Barcelona - where Gaudí masterpieces dance with the Mediterranean breeze. From the vibrant La Rambla to the architectural marvel of Sagrada Familia, each step is a journey through Catalan culture and artistic brilliance.',
     4, '2023-10-27 19:27:50'),

    ('Berlin Impressions - very steady and calm place to retire',
     'Berlin - a city where history meets innovation. From the remnants of the Berlin Wall to the modern architecture of Potsdamer Platz, each corner is a testament to the city’s resilience and creative spirit.',
     8, '2023-10-28 08:10:04'),

    ('Amsterdam Adventures - you cannot believe what I saw!',
     'Amsterdam - a city of canals and culture. From the historic Anne Frank House to the lively streets of Jordaan, each canal ride unveils a story of art, history, and the Dutch way of life.',
     9, '2023-10-29 17:45:32'),

    ('Prague Symphony - so much history to enjoy',
     'Prague - where medieval charm and Bohemian allure converge. From the majestic Prague Castle to the quaint streets of Mala Strana, each note of history resonates through the city’s cobblestone pathways.',
     10, '2023-10-30 13:20:17'),

    ('Singapore Sojourn - the future of Asia at your glance',
     'Singapore - a city-state where tradition meets modernity. From the futuristic skyline of Marina Bay Sands to the cultural richness of Chinatown, each moment is a harmonious blend of diverse influences.',
     11, '2023-10-31 11:05:56'),

    ('Dubai Dreams - the future is here',
     'Dubai - a city of opulence and architectural marvels. From the iconic Burj Khalifa to the luxury shopping in Dubai Mall, each skyline view is a testament to the city’s ambition and prosperity.',
     12, '2023-11-01 09:42:23'),

    ('Budapest Bliss - a place like no other',
     'Budapest - where the Danube River flows through history and thermal baths. From Buda Castle’s panoramic views to the vibrant ruin bars of Pest, each side of the river unveils a different facet of Hungarian charm.',
     13, '2023-11-02 15:18:57'),

    ('Seoul Serenity - such a surprise',
     'Seoul - a city that seamlessly blends ancient traditions with modern technology. From the historic Gyeongbokgung Palace to the bustling streets of Gangnam, each moment is a dynamic harmony of past and present.',
     14, '2023-11-03 12:55:32'),

    ('Madrid Mosaic - I cannot wait to come back',
     'Madrid - the heart of Spain, where art, history, and gastronomy converge. From the iconic Prado Museum to the lively Puerta del Sol, each tapas bite is a flavor of Spanish culture.',
     15, '2023-11-04 21:30:09'),

    ('Osaka Odyssey - cannot wait to visit again',
     'Osaka - a city known for its culinary delights and historic landmarks. From the vibrant street food scene in Dotonbori to the serene Osaka Castle, each bite and step is a journey through Japanese culture.',
     16, '2023-11-05 18:12:47'),

    ('Moscow Marvels - you have never seen something like it',
     'Moscow - where the Red Square stands as a symbol of Russian history and grandeur. From the iconic St. Basil’s Cathedral to the modern skyscrapers of Moscow City, each sight is a chapter in the city’s narrative.',
     17, '2023-11-06 14:55:21'),

    ('Hanoi Harmony - the best place on Earth',
     'Hanoi - the capital of Vietnam, where ancient temples and French colonial architecture coexist. From the serene Hoan Kiem Lake to the bustling streets of the Old Quarter, each moment is a blend of tradition and dynamism.',
     18, '2023-11-07 09:40:37'),

    ('Zurich Zen - a place to have fun in',
     'Zurich - a city where pristine lakes meet financial prowess. From the scenic shores of Lake Zurich to the contemporary art scene in Kunsthaus Zurich, each view is a moment of tranquility in the heart of Switzerland.',
     19, '2023-11-08 16:25:14'),

    ('Athens Adventures - the old soul of Greece',
     'Athens - the cradle of Western civilization, where ancient ruins stand amidst modern life. From the Acropolis majesty to the vibrant Plaka district, each step is a journey through the birthplace of democracy.',
     2, '2023-11-09 12:08:49');


INSERT INTO comments (comment, post_id, user_id)
VALUES
    ('Great post!', 1, 2),
    ('Amazing travel experiences!', 2, 3),
    ('Tech trends are changing rapidly.', 3, 1),
    ('Absolutely mesmerizing! Vienna has a unique charm that captivates the soul.', 1, 4),
    ('The history and melodies of Vienna create an enchanting atmosphere. Loved your perspective!', 1, 5),
    ('Belgrade resilience is truly inspiring. Your post beautifully captures its spirit.', 2, 1),
    ('The blend of ancient fortresses and vibrant nightlife makes Belgrade a hidden gem.', 2, 4),
    ('Plovdiv charm is undeniable.I felt transported to another era while wandering its streets.', 3, 2),
    ('The blend of ancient history and modern charm in Plovdiv is truly enchanting.', 3, 5),
    ('Tokyo is on my bucket list now! Your vivid description paints a captivating picture.', 4, 6),
    ('Paris, the city of love! Your post made me feel like I was strolling along the Seine.', 5, 7),
    ('Concrete Jungle Chronicles perfectly captures the energy of New York City!', 6, 8),
    ('Cape Town Tales paint a breathtaking picture of the natural beauty and cultural richness.', 7, 9),
    ('Sydney Serenade makes me want to book a flight and experience it firsthand!', 8, 10),
    ('Eternal City Impressions showcase the timeless beauty of Rome. Lovely post!', 9, 11),
    ('Istanbul Odyssey takes me on a virtual journey through the captivating city.', 10, 12),
    ('Rio Rhythms - I can almost hear the samba beats through your words. Amazing!', 11, 13),
    ('Bangkok Reverie - your description captures the essence of this vibrant city.', 12, 14);

INSERT INTO likes (post_id, user_id)
VALUES
    (1, 8),
    (1, 9),
    (1, 10),
    (2, 8),
    (2, 9),
    (2, 10),
    (3, 11),
    (3, 12),
    (3, 13),
    (4, 14),
    (4, 15),
    (4, 16),
    (5, 17),
    (5, 18),
    (5, 19),
    (6, 2),
    (6, 1),
    (6, 2),
    (7, 3),
    (7, 4),
    (7, 5),
    (8, 6),
    (8, 7),
    (8, 8),
    (9, 9),
    (9, 10),
    (9, 11),
    (10, 12),
    (10, 13),
    (10, 14);
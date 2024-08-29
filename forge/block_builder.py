import json
import os
import sys
from PIL import Image

# Helper functions
def create_directory(path):
    if not os.path.exists(path):
        os.makedirs(path)

def write_file(path, content):
    with open(path, 'w') as file:
        file.write(content)

def update_file(path, new_content):
    # Parse new_content as a dictionary
    new_data = json.loads(new_content)
    
    with open(path, 'r+') as file:
        try:
            # Load existing content as a dictionary
            content = json.load(file)
        except json.JSONDecodeError:
            # If the file is empty or invalid, start with an empty dictionary
            content = {}
        
        # Update content with new data
        content.update(new_data)
        
        # Rewind the file and overwrite with updated content
        file.seek(0)
        json.dump(content, file, indent=4)
        file.truncate()


def read_template(file_path, replacements):
    with open(file_path, 'r') as file:
        content = file.read()
        for key, value in replacements.items():
            content = content.replace("{" + key + "}", value)
    return content

def create_white_texture(mod_id, block_name, size=(16, 16)):
    # Define the directories
    base_dir = './src/main/resources/assets'
    texture_block_dir = f'{base_dir}/{mod_id}/textures/block/'
    texture_item_dir = f'{base_dir}/{mod_id}/textures/item/'
    
    # Create directories if they don't exist
    os.makedirs(texture_block_dir, exist_ok=True)
    os.makedirs(texture_item_dir, exist_ok=True)
    
    # Define the file paths
    block_texture_path = f'{texture_block_dir}/{block_name.lower()}.png'
    item_texture_path = f'{texture_item_dir}/{block_name.lower()}.png'
    
    # Create a white image
    white_image = Image.new('RGBA', size, (255, 255, 255, 255))
    
    # Save the image as both block and item textures
    white_image.save(block_texture_path)
    white_image.save(item_texture_path)
    
    print(f'Created white texture images for "{block_name}" in mod "{mod_id}".')



# Main function
def main(mod_id, block_name):
    base_dir = './src/main'
    
    # Convert block name to lowercase, uppercase, and title case
    block_lower = block_name.lower()
    block_upper = block_name.upper()
    block_title = block_name.replace("_", " ").title()
    
    # Paths for new files
    java_block_dir = f'{base_dir}/java/com/example/{mod_id}/block/'
    java_item_dir = f'{base_dir}/java/com/example/{mod_id}/item/'
    assets_dir = f'{base_dir}/resources/assets/{mod_id}/'
    data_dir = f'{base_dir}/resources/data/{mod_id}/'
    
    # Create directories if they don't exist
    create_directory(java_block_dir)
    create_directory(f'{assets_dir}/textures/block/')
    create_directory(f'{assets_dir}/textures/item/')
    create_directory(f'{assets_dir}/blockstates/')
    create_directory(f'{assets_dir}/models/block/')
    create_directory(f'{assets_dir}/models/item/')
    create_directory(f'{data_dir}/loot_tables/blocks/')
    
    # Template replacements
    replacements = {
        "mod_id": mod_id,
        "block_name_upper": block_upper,
        "block_lower": block_lower,
        "block_name_title": block_title
    }
    
    blockstate_content = read_template('./src/templates/blockstate.json.template', replacements)
    write_file(f'{assets_dir}/blockstates/{block_lower}.json', blockstate_content)
    
    block_model_content = read_template('./src/templates/block_model.json.template', replacements)
    write_file(f'{assets_dir}/models/block/{block_lower}.json', block_model_content)
    
    item_model_content = read_template('./src/templates/item_model.json.template', replacements)
    write_file(f'{assets_dir}/models/item/{block_lower}.json', item_model_content)
    
    loot_table_content = read_template('./src/templates/loot_table.json.template', replacements)
    write_file(f'{data_dir}/loot_tables/blocks/{block_lower}.json', loot_table_content)
    
    lang_path = f'{assets_dir}/lang/en_us.json'
    lang_update = read_template('./src/templates/lang_entry.json.template', replacements)
    update_file(lang_path, lang_update)
    
    create_white_texture(mod_id, block_lower)

    print(f'Successfully created and updated files for block "{block_name}" in mod "{mod_id}".')

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python block_builder.py <mod_id> <block_name>")
    else:
        main(sys.argv[1], sys.argv[2])
